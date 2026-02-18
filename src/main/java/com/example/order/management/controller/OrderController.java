package com.example.order.management.controller;

import com.example.order.management.dto.OrderRequest;
import com.example.order.management.dto.OrderResponse;
import com.example.order.management.service.OrderService;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService service;
    private final Counter counter;

    public OrderController(OrderService service, MeterRegistry registry){
        this.service = service;
        this.counter = registry.counter("orders.created");
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('CREATE_ORDER')")
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
    @PreAuthorize("hasRole('ADMIN') and hasRole('USER')")
    public OrderResponse cancelOrder(@PathVariable Long id){
        return service.cancelOrder(id);
    }

    @GetMapping("/security")
    @PreAuthorize("hasRole('ADMIN')")
    public String testSecurity(){
        counter.increment();
        return "This is security test method";
    }
}
