package com.banking_app.banking_app.exceptions;

public class BadCredentialsException extends RuntimeException {
    public BadCredentialsException() {
        super("Bad credentials");
    }
    public BadCredentialsException(String message) {
        super(message);
    }
}
