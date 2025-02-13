package com.example.order_service.service.Impl;
import com.example.order_service.DTO.OrderDTO;
import com.example.order_service.DTO.OrderEvent;
import com.example.order_service.DTO.OrderItemDTO;
import com.example.order_service.DTO.ProductDTO;
import com.example.order_service.client.ProductClient;
import com.example.order_service.kafka.OrderProducer;
import com.example.order_service.mapper.OrderItemMapper;
import com.example.order_service.mapper.OrderMapper;
import com.example.order_service.model.Order;
import com.example.order_service.model.OrderItem;
import com.example.order_service.repository.OrderRepository;
import com.example.order_service.service.OrderService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper mapper;
    private final OrderItemMapper orderItemMapper;
    private final ProductClient productClient;
    private final OrderProducer orderProducer;


    @Override
    public List<OrderDTO> getAll() {
         return orderRepository.findAll().stream().map(mapper::toDTO).toList();
    }

    @Override
    public OrderDTO create(OrderDTO orderDTO) {
        Order order = mapper.toEntity(orderDTO);
        order.setCreatedAt(LocalDateTime.now());
        List<OrderItem> orderItems = prepareOrderItemsList(orderDTO.getOrderItems(), order);
        order.setOrderItems(orderItems);
        BigDecimal totalPrice = orderItems.stream().map(OrderItem::getTotalPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        order.setTotalPrice(totalPrice);
        Order created = orderRepository.save(order);
        orderProducer.sendNotification(
                new OrderEvent(
                        created.getUserId(),
                        created.getId()
                )
        );
        return mapper.toDTO(created);
    }

    @Override
    public OrderDTO getOne(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Order not found with id " + id));
        return mapper.toDTO(order);
    }

    @Override
    public void delete(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Order not found with id " + id));
        orderRepository.delete(order);

    }

    private List<OrderItem> prepareOrderItemsList(List<OrderItemDTO> orderItemDTOS, Order order) {
        return orderItemDTOS.stream()
                .map(orderItemDto -> {
                    OrderItem orderItem =  orderItemMapper.toEntity(orderItemDto);
                    ProductDTO product = productClient.getOne(orderItem.getProductId());
                    orderItem.setPrice(product.getPrice());
                    orderItem.setTotalPrice(product.getPrice().multiply(BigDecimal.valueOf(orderItem.getQuantity())));
                    orderItem.setOrder(order);
                    return orderItem;
                }).toList();
    }
}
