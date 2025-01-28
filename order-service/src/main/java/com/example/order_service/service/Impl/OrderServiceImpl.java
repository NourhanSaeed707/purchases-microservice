package com.example.order_service.service.Impl;
import com.example.order_service.repository.OrderRepository;
import com.example.order_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
}
