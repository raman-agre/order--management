package com.example.order.management.controller;

import com.example.order.management.dto.OrderRequest;
import com.example.order.management.dto.OrderResponse;
import com.example.order.management.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service){
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse createOrder(@Valid @RequestBody OrderRequest request){
        return service.createOrder(request);
    }

    @GetMapping("/{id}")
    public OrderResponse getOrder(@PathVariable Long id){
        return service.getOrder(id);
    }

    @GetMapping
    public List<OrderResponse> getOrderByCustomer(@RequestParam String customerId){
        return service.getOrderByCustomer(customerId);
    }

    @PutMapping("/{id}/cancel")
    public OrderResponse cancelOrder(@PathVariable Long id){
        return service.cancelOrder(id);
    }
}
