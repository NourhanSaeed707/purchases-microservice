package com.example.notification_service.client;
import com.example.notification_service.DTO.UserDTO;
import com.example.notification_service.DTO.Users;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "user-service", url = "${application.config.user-service-url}")
public interface UserClient {

    @GetMapping("/get-user-by-id/{id}")
    UserDTO getUserById(@PathVariable("id") Long id, @RequestHeader("Authorization") String token);

    @GetMapping("/get-by-email/{email}")
    Users getUserByEmail(@PathVariable("email") String email, @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader);
}
