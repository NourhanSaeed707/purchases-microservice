package com.example.order_service.service;

import com.example.order_service.DTO.OrderDTO;

import java.util.List;

public interface OrderService {
    List<OrderDTO> getAll();

    OrderDTO create(OrderDTO orderDTO, String token);

    OrderDTO getOne(String token, Long id);
}
