package com.example.order.management;

import com.example.order.management.controller.OrderController;
import com.example.order.management.dto.OrderRequest;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ApplicationTests {

	@Mock
	OrderController controller;

	@Test
	void contextLoads() {
	}

	@Test
	void testCreateOrder(OrderRequest request){
		controller.createOrder(request);
	}


}
