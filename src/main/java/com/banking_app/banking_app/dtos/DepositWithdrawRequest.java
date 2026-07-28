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
public class DepositWithdrawRequest {
    @NotNull(message = "Missing amount")
    @NotNull @Positive(message = "Amount must be positive")
    BigDecimal amount;

    @NotNull(message = "Bad account id")
    Long accountId;
}
