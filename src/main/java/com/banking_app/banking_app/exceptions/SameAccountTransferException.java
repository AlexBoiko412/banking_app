package com.banking_app.banking_app.exceptions;

public class SameAccountTransferException extends RuntimeException {
    public SameAccountTransferException(Long id) {
        super("Transfer to the same account: " + id);
    }
}
