package com.banking_app.banking_app.exceptions;

import java.math.BigDecimal;

public class InsufficientBalance extends RuntimeException {
    public InsufficientBalance(BigDecimal balance, BigDecimal amount) {

        super(amount + " more than balance: " + balance);
    }
}
