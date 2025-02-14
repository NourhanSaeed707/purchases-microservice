package com.example.notification_service.client;
import com.example.notification_service.DTO.ProductDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "product-service", url = "${application.config.product-url}")
public interface ProductClient {
    @GetMapping("/{id}")
    ProductDTO getOne(@PathVariable Long id, @RequestHeader("Authorization") String token );
}
