package com.banking_app.banking_app.dtos;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {
    Long id;
    String email;
    String firstName;
    String lastName;
    String address;
}
