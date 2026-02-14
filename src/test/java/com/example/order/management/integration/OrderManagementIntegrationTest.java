package com.example.order.management.integration;

import com.example.order.management.dto.OrderRequest;
import com.example.order.management.dto.OrderResponse;
import com.example.order.management.enums.OrderStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrderManagementIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private String baseUrl;

    @BeforeEach
    void setUp(){
        baseUrl = "http://localhost:" + port + "/orders";
    }

    @Test
    void testCreateOrder(){

        OrderRequest request = new OrderRequest();
        request.setCustomerId("105");
        request.setAmount(3000);

        OrderResponse response = new OrderResponse();
        response.setCustomerId("105");
        response.setAmount(3000);
        response.setStatus(OrderStatus.CREATED);

        OrderResponse testResponse = restTemplate.postForObject(baseUrl, request, OrderResponse.class);

        assertNotNull(testResponse);
        assertNotNull(testResponse.getCustomerId());
        assertEquals(response.getCustomerId(), testResponse.getCustomerId());
        assertEquals(response.getAmount(), testResponse.getAmount());
        assertEquals("CREATED", testResponse.getStatus().toString());
    }

    @Test
    void testGetOrder(){
        Long id = 3L;

        baseUrl = "http://localhost:" + port + "/orders/" + id;

        OrderResponse response = new OrderResponse();
        response.setCustomerId("105");
        response.setAmount(3000);
        response.setStatus(OrderStatus.CREATED);

        OrderResponse testResponse = restTemplate.getForObject(baseUrl, OrderResponse.class);

        assertEquals(response.getCustomerId(), testResponse.getCustomerId());
        assertEquals("CREATED", testResponse.getStatus().toString());
        assertEquals(id, testResponse.getId());
    }
}
