package com.banking_app.banking_app.dtos;

import com.banking_app.banking_app.enums.AccountType;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public class AccountResponse {
    Long id;
    AccountType type;
    BigDecimal balance;
    Long userId;
}
