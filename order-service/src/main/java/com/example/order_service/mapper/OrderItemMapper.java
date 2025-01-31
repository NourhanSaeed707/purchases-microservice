package com.example.order_service.mapper;
import com.example.order_service.DTO.OrderItemDTO;
import com.example.order_service.model.OrderItem;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemMapper {
    public OrderItem toEntity(OrderItemDTO orderItemDto) {
        return OrderItem.builder()
                .id(orderItemDto.getId())
                .price(orderItemDto.getPrice())
                .quantity(orderItemDto.getQuantity())
                .productId(orderItemDto.getProductId())
                .build();
    }

    public OrderItemDTO toDTO(OrderItem orderItems) {
        return OrderItemDTO.builder()
                .id(orderItems.getId())
                .productId(orderItems.getProductId())
                .price(orderItems.getPrice())
                .totalPrice(orderItems.getTotalPrice())
                .quantity(orderItems.getQuantity())
                .build();
    }
}
