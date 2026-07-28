package com.banking_app.banking_app.security;

import com.banking_app.banking_app.entities.User;
import com.banking_app.banking_app.exceptions.UserNotFoundException;
import com.banking_app.banking_app.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CurrentUserService {
    private final AuthenticationFacade authenticationFacade;
    private final UserRepository userRepository;

    public User loadCurrentUser() {
        String email = authenticationFacade.getUsername();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));

        return user;
    }
}