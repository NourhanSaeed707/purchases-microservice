package com.example.product_service.client;
import com.example.product_service.DTO.Users;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "user-service", url = "${application.config.user-service-url}")
public interface UserClient {

    @GetMapping("/get-by-email/{email}")
    Users getUserByEmail(@PathVariable("email") String email, @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader);

}
