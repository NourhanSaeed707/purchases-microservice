package com.example.order_service.client;
import com.example.order_service.DTO.Users;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import java.util.Optional;

@FeignClient(name = "user-service")
public interface UserClient {
    @GetMapping("/api/v1/auth/user")
    ResponseEntity<Optional<Users>> getUserInfo(@RequestHeader("Authorization") String token);
}