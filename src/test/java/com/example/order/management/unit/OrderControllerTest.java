package com.example.order.management.unit;

import com.example.order.management.controller.OrderController;
import com.example.order.management.dto.OrderRequest;
import com.example.order.management.dto.OrderResponse;
import com.example.order.management.entity.Order;
import com.example.order.management.enums.OrderStatus;
import com.example.order.management.repository.OrderRepository;
import com.example.order.management.service.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderControllerTest {

    @Mock
    OrderService service;

    @InjectMocks
    OrderController controller;

    @Test
    void testCreateOrder(){
        OrderRequest order = new OrderRequest();
        order.setCustomerId("21");
        order.setAmount(1000);

        OrderResponse response = new OrderResponse();
        response.setCustomerId("21");
        response.setAmount(1000);
        response.setStatus(OrderStatus.CREATED);

        when(service.createOrder(order)).thenReturn(response);

        OrderResponse testResponse = controller.createOrder(order);

        assertNotNull(testResponse);

        assertEquals(response.getCustomerId(), testResponse.getCustomerId());
        assertEquals(response.getAmount(), testResponse.getAmount());
        assertEquals(response.getStatus(), testResponse.getStatus());
    }
}
