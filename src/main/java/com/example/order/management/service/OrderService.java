package com.example.order.management.service;

import com.example.order.management.dto.OrderRequest;
import com.example.order.management.dto.OrderResponse;
import com.example.order.management.entity.Order;
import com.example.order.management.enums.OrderStatus;
import com.example.order.management.repository.OrderRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository repository;

    public OrderService(OrderRepository repository){
         this.repository = repository;
    }

    public OrderResponse createOrder(OrderRequest request){
        Order order = new Order();
        order.setCustomerId(request.getCustomerId());
        order.setAmount(request.getAmount());
        order.setStatus(OrderStatus.CREATED);
        return mapToResponse(repository.save(order));
    }

    public OrderResponse getOrder(Long id){
        Order order = repository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
        return mapToResponse(order);
    }

    public List<OrderResponse> getOrderByCustomer(String customerId){
//        List<Order> orderList = repository.findByCustomerId(customerId);
//        return orderList.stream().map(this::mapToResponse).collect(Collectors.toList());
        return repository.findByCustomerId(customerId).stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    public OrderResponse cancelOrder(Long id){
        Order order = repository.findById(id).get();
        order.setStatus(OrderStatus.CANCELLED);
        return mapToResponse(repository.save(order));
    }

    public OrderResponse mapToResponse(Order order){
        OrderResponse response = new OrderResponse();
        response.setId(order.getId());
        response.setCustomerId(order.getCustomerId());
        response.setAmount(order.getAmount());
        response.setStatus(order.getStatus());
        response.setCreatedAt(order.getCreatedAt());
        return response;
    }
}
