package com.banking_app.banking_app.dtos;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class DepositWithdrawRequest {
    BigDecimal amount;
    Long accountId;
}
