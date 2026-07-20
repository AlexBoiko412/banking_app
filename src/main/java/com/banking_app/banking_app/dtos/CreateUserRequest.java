package com.banking_app.banking_app.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateUserRequest {
    @NotBlank(message = "Missing email")
    @Email(message = "Email is wrong format")
    String email;

    @NotBlank(message = "Missing login")
    @Size(min = 5, message = "Login must be longer than 5")
    String login;

    @NotBlank(message = "Missing firstname")
    String firstName;

    @NotBlank(message = "Missing lastname")
    String lastName;

    @NotBlank(message = "Missing address")
    String address;

    @NotBlank(message = "Missing password")
    @Size(min = 8, message = "Password must be longer than 8")
    String password;
}