package com.banking_app.banking_app.dtos;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class TransferRequest {
    Long senderId;
    Long receiverId;
    BigDecimal amount;
}
