package com.banking_app.banking_app.exceptions;

import java.math.BigDecimal;

public class InvalidDepositAmount extends RuntimeException {
    public InvalidDepositAmount(BigDecimal amount) {

        super("Invalid deposit amount: " + amount);
    }
}
