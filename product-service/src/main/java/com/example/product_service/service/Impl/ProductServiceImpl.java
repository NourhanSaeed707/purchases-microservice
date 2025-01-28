package com.example.product_service.service.Impl;
import com.example.product_service.repository.ProductRepository;
import com.example.product_service.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;


}
