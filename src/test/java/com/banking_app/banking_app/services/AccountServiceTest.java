package com.banking_app.banking_app.services;

import com.banking_app.banking_app.entities.Account;
import com.banking_app.banking_app.entities.User;
import com.banking_app.banking_app.enums.AccountType;
import com.banking_app.banking_app.exceptions.AccountNotFoundException;
import com.banking_app.banking_app.exceptions.ActionForbidden;
import com.banking_app.banking_app.repositories.AccountRepository;
import com.banking_app.banking_app.repositories.UserRepository;
import com.banking_app.banking_app.security.interfaces.IAuthenticationFacade;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;



@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {
    @Mock
    AccountRepository accountRepository;
    @Mock
     UserRepository userRepository;
    @Mock
     IAuthenticationFacade authenticationFacade;

    @InjectMocks
    AccountService accountService;

    @DisplayName("Create account another user throws action forbidden")
    @Test
    void createAccount_userNotOwner_throwsActionForbidden() {
        when(authenticationFacade.getUsername()).thenReturn("user2");

        when(userRepository.findById(1L)).thenReturn(Optional.of(User.builder()
                                                                  .id(1L)
                                                                  .email("user")
                                                                  .build()));

        assertThrows(ActionForbidden.class, () -> accountService.createAccount(1L, AccountType.SAVINGS));
    }

    @DisplayName("Get balance with wrong auth user")
    @Test
    void getBalance_userNotOwner_throwsAccountNotFoundException() {
        when(authenticationFacade.getUsername()).thenReturn("user2");

        when(accountRepository.findByIdAndUser_Email(1L, "user2")).thenReturn(Optional.empty());

        assertThrows(AccountNotFoundException.class, () -> accountService.getBalance(1L));
    }

    @DisplayName("Create account valid user saves account")
    @Test
    void createAccount_validUser_savesAccount() {
        when(authenticationFacade.getUsername()).thenReturn("user");

        when(userRepository.findById(1L)).thenReturn(Optional.of(User.builder()
                                                                     .id(1L)
                                                                     .email("user")
                                                                     .build()));

        Account account = Account.builder()
            .id(1L)
            .type(AccountType.SAVINGS)
            .build();

        when(accountRepository.save(any())).thenReturn(account);

        assertThat(accountService.createAccount(1L, AccountType.SAVINGS)).returns(1L, Account::getId);

        Account savedAccount = Account.builder()
                                 .user(User.builder()
                                           .email("user")
                                           .id(1L)
                                           .build()
                                 )
                                 .balance(BigDecimal.ZERO)
                                 .type(AccountType.SAVINGS)
                                 .build();

        verify(accountRepository).save(savedAccount);
    }

    @DisplayName("Get accounts byt id valid user returns owned accounts")
    @Test
    void getAccountsByUserId_returnsOnlyOwnAccounts() {
        when(authenticationFacade.getUsername()).thenReturn("user");

        List<Account> accounts = List.of(
            Account.builder()
                .id(1L)
                .user(User.builder()
                          .email("user")
                          .build())
                .build(),
            Account.builder()
               .id(2L)
               .user(User.builder()
                         .email("user")
                         .build())
               .build()
        );

        when(accountRepository.findAllByUserIdAndUser_Email(1L, "user"))
            .thenReturn(accounts);

        assertThat(accountService.getAccountsByUserId(1L))
            .containsExactlyInAnyOrder(accounts.get(0), accounts.get(1));
    }
}
