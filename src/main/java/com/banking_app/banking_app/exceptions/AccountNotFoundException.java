package com.banking_app.banking_app.exceptions;

public class AccountNotFoundException extends RuntimeException {
    public AccountNotFoundException(Long id) {
        super("Account with id: " + id + " not found.");
    }
}
