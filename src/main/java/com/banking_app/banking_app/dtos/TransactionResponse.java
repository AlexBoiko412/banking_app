package com.banking_app.banking_app.dtos;

import com.banking_app.banking_app.enums.TransactionType;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class TransactionResponse {
    Long id;
    Long senderId;
    Long receiverId;
    LocalDateTime fulfilledAt;
    BigDecimal amount;
    TransactionType type;
}
