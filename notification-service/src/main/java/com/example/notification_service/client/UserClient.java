package com.example.notification_service.client;
import com.example.notification_service.DTO.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service", url = "${application.config.user-service-url}")
public interface UserClient {

    @GetMapping("/get-user-by-id/{id}")
    UserDTO getUserById(@PathVariable("id") Long id);
}
