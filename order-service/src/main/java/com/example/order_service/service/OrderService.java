package com.example.order_service.service;

import com.example.order_service.DTO.OrderDTO;

import java.util.List;

public interface OrderService {
    List<OrderDTO> getAll();
}
