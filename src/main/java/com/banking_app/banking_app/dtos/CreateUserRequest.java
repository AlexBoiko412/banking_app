package com.banking_app.banking_app.dtos;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateUserRequest {
    String email;
    String login;
    String firstName;
    String lastName;
    String address;
    String password;
}