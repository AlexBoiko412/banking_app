package com.banking_app.banking_app.exceptions;

public class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException(String email) {
        super("User with that email already exits: " + email);
    }
}
