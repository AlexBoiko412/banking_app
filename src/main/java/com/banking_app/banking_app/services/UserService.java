package com.banking_app.banking_app.services;


import com.banking_app.banking_app.entities.User;
import com.banking_app.banking_app.exceptions.UserNotFoundException;
import com.banking_app.banking_app.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User createUser(
            String email,
            String login,
            String firstName,
            String lastName,
            String address,
            String password
    ) {
        User user = User.builder()
                .login(login)
                .address(address)
                .email(email)
                .firstName(firstName)
                .lastName(lastName)
                .passwordHash(password)
                .build();

        return userRepository.save(user);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }


}
