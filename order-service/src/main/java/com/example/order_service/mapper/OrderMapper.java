package com.example.order_service.mapper;
import com.example.order_service.DTO.OrderDTO;
import com.example.order_service.model.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderMapper {
    private final OrderItemMapper orderItemMapper;
    public Order toEntity(OrderDTO orderDTO) {
        return Order.builder()
                .id(orderDTO.getId())
                .userId(orderDTO.getUserId())
                .deliveryAddress(orderDTO.getDeliveryAddress())
                .createdAt(orderDTO.getCreatedAt())
                .updatedAt(orderDTO.getUpdatedAt())
                .orderItems(orderDTO.getOrderItems().stream().map(orderItemMapper::toEntity).toList())
                .build();
    }

    public OrderDTO toDTO(Order order) {
        return OrderDTO.builder()
                .id(order.getId())
                .userId(order.getUserId())
                .createdAt(order.getCreatedAt())
                .updatedAt(order.getUpdatedAt())
                .deliveryAddress(order.getDeliveryAddress())
                .orderItems(order.getOrderItems().stream().map(orderItemMapper::toDTO).toList())
                .totalPrice(order.getTotalPrice())
                .build();
    }
}
