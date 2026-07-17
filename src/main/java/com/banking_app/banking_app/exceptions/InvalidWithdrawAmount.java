package com.banking_app.banking_app.exceptions;

import java.math.BigDecimal;

public class InvalidWithdrawAmount extends RuntimeException {
    public InvalidWithdrawAmount(BigDecimal amount) {

        super("Invalid withdraw amount: " + amount);
    }
}
