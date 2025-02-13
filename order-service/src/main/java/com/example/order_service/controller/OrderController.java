package com.example.order_service.controller;
import com.example.order_service.DTO.OrderDTO;
import com.example.order_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public List<OrderDTO> getAll() {
        return orderService.getAll();
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public OrderDTO create( @RequestBody OrderDTO orderDTO) {
        return orderService.create(orderDTO ) ;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OrderDTO getOne(@PathVariable("id") Long id) {
        return orderService.getOne(id);

    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete ( @PathVariable("id") Long id) {
        orderService.delete(id);
    }

}
