package com.banking_app.banking_app.dtos;

import com.banking_app.banking_app.enums.AccountType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateAccountRequest {
    @NotNull(message = "Bad user")
    Long userId;
    @NotNull(message = "Bad Account type")
    AccountType type;
}
