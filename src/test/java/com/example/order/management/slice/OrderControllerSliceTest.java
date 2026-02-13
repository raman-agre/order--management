package com.example.order.management.slice;

import com.example.order.management.controller.OrderController;
import com.example.order.management.dto.OrderRequest;
import com.example.order.management.dto.OrderResponse;
import com.example.order.management.enums.OrderStatus;
import com.example.order.management.service.OrderService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import static com.example.order.management.enums.OrderStatus.CREATED;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
@AutoConfigureMockMvc
public class OrderControllerSliceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private OrderService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateOrder() throws Exception {

        OrderRequest request = new OrderRequest();
        request.setCustomerId("110");
        request.setAmount(2500);

        OrderResponse response = new OrderResponse();
        response.setId(1L);
        response.setCustomerId("110");
        response.setAmount(1000);
        response.setStatus(CREATED);

        when(service.createOrder(request)).thenReturn(response);

        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.customerId").value("110"))
                .andExpect(jsonPath("$.amount").value(1000))
                .andExpect(jsonPath("$.status").value("CREATED"));
    }

    @Test
    void testGetOrder() throws Exception {

        Long orderId = 2L;

        OrderResponse response = new OrderResponse();
        response.setId(2L);
        response.setCustomerId("305");
        response.setAmount(1500);
        response.setStatus(CREATED);

        Mockito.when(service.getOrder(2L)).thenReturn(response);

        mockMvc.perform(get("/orders/{id}", orderId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.customerId").value("305"))
                .andExpect(jsonPath("$.amount").value(1500))
                .andExpect(jsonPath("$.status").value("CREATED"));
    }
}
