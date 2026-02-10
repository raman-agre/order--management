package com.example.order.management.dto;

import com.example.order.management.enums.OrderStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderResponse {

     private Long id;
     private String customerId;
     private int amount;
     private OrderStatus status;
     private LocalDateTime createdAt;
}
