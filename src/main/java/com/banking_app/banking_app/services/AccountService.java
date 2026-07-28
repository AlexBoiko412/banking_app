package com.banking_app.banking_app.services;


import com.banking_app.banking_app.entities.Account;
import com.banking_app.banking_app.entities.User;
import com.banking_app.banking_app.enums.AccountType;
import com.banking_app.banking_app.exceptions.*;
import com.banking_app.banking_app.repositories.AccountRepository;
import com.banking_app.banking_app.repositories.UserRepository;
import com.banking_app.banking_app.security.interfaces.IAuthenticationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final IAuthenticationFacade authenticationFacade;

    public Account createAccount(Long userId, AccountType type) {

        User user = userRepository
                .findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        if(!user.getEmail()
                .equals(authenticationFacade.getUsername()))
            throw new ActionForbidden();

        Account account = Account.builder()
                .user(user)
                .balance(BigDecimal.ZERO)
                .type(type)
                .build();

        return accountRepository.save(account);
    }

    public BigDecimal getBalance(Long id) {
        String email = authenticationFacade.getUsername();

        Account account = accountRepository
                .findByIdAndUser_Email(id, email)
                .orElseThrow(() -> new AccountNotFoundException(id));


        return account.getBalance();
    }

    public List<Account> getAccountsByUserId(Long userId) {

        return accountRepository.findAllByUserIdAndUser_Email(
                userId,
                authenticationFacade.getUsername()
        );
    }
}
