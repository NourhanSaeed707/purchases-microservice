package com.example.order_service.mapper;
import com.example.order_service.model.OrderItem;
import org.springframework.stereotype.Service;

@Service
public class OrderItemMapper {
    public OrderItem toEntity(OrderItem orderItemDto) {
        return OrderItem.builder()
                .id(orderItemDto.getId())
                .price(orderItemDto.getPrice())
                .quantity(orderItemDto.getQuantity())
                .productId(orderItemDto.getProductId())
                .build();
    }
}
