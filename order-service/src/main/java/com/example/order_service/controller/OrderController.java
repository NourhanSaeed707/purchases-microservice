package com.example.order_service.controller;
import com.example.order_service.DTO.OrderDTO;
import com.example.order_service.DTO.Role;
import com.example.order_service.DTO.Users;
import com.example.order_service.client.UserClient;
import com.example.order_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final UserClient userClient;

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public List<OrderDTO> getAll(@RequestHeader("Authorization") String token) {
        ResponseEntity<Optional<Users>> userResponse = userClient.getUserInfo(token);
        Users user = userResponse.getBody().orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));
        if (user.getRole() != Role.ADMIN) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You do not have permission to access this resource");
        }
        return orderService.getAll();
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public OrderDTO create(@RequestHeader("Authorization") String token, @RequestBody OrderDTO orderDTO) {
        ResponseEntity<Optional<Users>> userResponse = userClient.getUserInfo(token);
        Users user = userResponse.getBody().orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));
        if (user.getRole() != Role.ADMIN) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You do not have permission to access this resource");
        }
        return orderService.create(orderDTO) ;
    }

}
