package com.banking_app.banking_app.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransferRequest {
    @NotNull(message = "Bad sender id")
    Long senderId;

    @NotNull(message = "Bad receiver id")
    Long receiverId;

    @NotNull(message = "Missing transfer amount")
    @Positive(message = "Amount must be positive")
    BigDecimal amount;
}
