package com.banking_app.banking_app.controllers;

import com.banking_app.banking_app.dtos.CreateUserRequest;
import com.banking_app.banking_app.dtos.UserResponse;
import com.banking_app.banking_app.entities.User;
import com.banking_app.banking_app.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable Long id) {
        User user = userService.getUserById(id);

        return ResponseEntity.ok(toUserResponse(user));
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody CreateUserRequest body) {
        User user = userService.createUser(
                body.getEmail(),
                body.getLogin(),
                body.getFirstName(),
                body.getLastName(),
                body.getAddress(),
                body.getPassword()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(toUserResponse(user));
    }

    private UserResponse toUserResponse(User user) {
        return UserResponse.builder()
                .address(user.getAddress())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .id(user.getId())
                .build();
    }
}
