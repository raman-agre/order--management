package com.example.order.management.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderRequest {

    @NotBlank
    private String customerId;

    @NotNull
    private int amount;
}
