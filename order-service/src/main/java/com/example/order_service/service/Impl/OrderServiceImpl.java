package com.example.order_service.service.Impl;
import com.example.order_service.DTO.OrderDTO;
import com.example.order_service.mapper.OrderItemMapper;
import com.example.order_service.mapper.OrderMapper;
import com.example.order_service.model.Order;
import com.example.order_service.model.OrderItem;
import com.example.order_service.repository.OrderRepository;
import com.example.order_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper mapper;
    private final OrderItemMapper orderItemMapper;

    @Override
    public List<OrderDTO> getAll() {
        return List.of();
    }

    @Override
    public OrderDTO create(OrderDTO orderDTO) {
        Order order = mapper.toEntity(orderDTO);
        order.setCreatedAt(LocalDateTime.now());
        List<OrderItem> orderItems = orderDTO.getOrderItems().stream()
                .map(orderItemDto -> {
                    OrderItem orderItem =  orderItemMapper.toEntity(orderItemDto);
                    orderItem.setTotalPrice(orderItem.getPrice().multiply(BigDecimal.valueOf(orderItem.getQuantity())));
                    return orderItem;
                }).toList();

        order.setOrderItems(orderItems);
        BigDecimal totalPrice = orderItems.stream().map(OrderItem::getTotalPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        order.setTotalPrice(totalPrice);
        orderRepository.save(order);
        return orderDTO;
    }
}
