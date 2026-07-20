package com.banking_app.banking_app.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepositWithdrawRequest {
    @NotNull(message = "Missing amount")
    BigDecimal amount;

    @NotNull(message = "Bad account id")
    Long accountId;
}
