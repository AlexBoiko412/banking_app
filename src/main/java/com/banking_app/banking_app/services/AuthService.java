package com.banking_app.banking_app.services;

import com.banking_app.banking_app.dtos.CreateUserRequest;
import com.banking_app.banking_app.entities.User;
import com.banking_app.banking_app.enums.UserRole;
import com.banking_app.banking_app.exceptions.BadCredentialsException;
import com.banking_app.banking_app.exceptions.EmailAlreadyExistsException;
import com.banking_app.banking_app.exceptions.LoginAlreadyExistsException;
import com.banking_app.banking_app.exceptions.UserNotFoundException;
import com.banking_app.banking_app.repositories.UserRepository;
import com.banking_app.banking_app.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public String login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));

        if(!passwordEncoder.matches(password, user.getPasswordHash())) {
            throw new BadCredentialsException();
        }

        return jwtUtil.generateToken(email, user.getLogin());
    }

    public String register(CreateUserRequest registerDto) {
        if(userRepository.existsByEmail(registerDto.getEmail()))
            throw new EmailAlreadyExistsException(registerDto.getEmail());
        if(userRepository.existsByLogin(registerDto.getLogin()))
            throw new LoginAlreadyExistsException(registerDto.getLogin());

        User user = userRepository.save(
                User.builder()
                        .email(registerDto.getEmail())
                        .login(registerDto.getLogin())
                        .firstName(registerDto.getFirstName())
                        .lastName(registerDto.getLastName())
                        .address(registerDto.getAddress())
                        .role(UserRole.USER)
                        .passwordHash(passwordEncoder.encode(
                                registerDto.getPassword()
                        ))
                        .build()
        );

        return jwtUtil.generateToken(user.getEmail(), user.getLogin());
    }
}
