package com.example.order_service.client;
import com.example.order_service.DTO.ProductDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "product-service", url = "${application.config.product-url}")
public interface ProductClient {
    @GetMapping("/{id}")
    public ProductDTO getOne(@PathVariable Long id, @RequestHeader("Authorization") String token);
}
