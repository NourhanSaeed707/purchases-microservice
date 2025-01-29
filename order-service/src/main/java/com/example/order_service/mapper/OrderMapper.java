package com.example.order_service.mapper;
import com.example.order_service.DTO.OrderDTO;
import com.example.order_service.model.Order;
import org.springframework.stereotype.Service;

@Service
public class OrderMapper {
    public Order toEntity(OrderDTO orderDTO) {
        return Order.builder()
                .id(orderDTO.getId())
                .userId(orderDTO.getUserId())
                .deliveryAddress(orderDTO.getDeliveryAddress())
                .build();
    }
}
