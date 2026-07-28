package com.banking_app.banking_app.exceptions;

public class LoginAlreadyExistsException extends RuntimeException {
    public LoginAlreadyExistsException(String login) {

        super("User with that login already exits: " + login);
    }
}
