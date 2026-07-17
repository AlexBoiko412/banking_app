package com.banking_app.banking_app.dtos;

import com.banking_app.banking_app.enums.AccountType;
import lombok.Getter;

@Getter
public class CreateAccountRequest {
    Long userId;
    AccountType type;
}
