package com.banking_app.banking_app.services;

import com.banking_app.banking_app.entities.Account;
import com.banking_app.banking_app.entities.Transaction;
import com.banking_app.banking_app.entities.User;
import com.banking_app.banking_app.exceptions.InsufficientBalance;
import com.banking_app.banking_app.exceptions.InvalidWithdrawAmount;
import com.banking_app.banking_app.exceptions.SameAccountTransferException;
import com.banking_app.banking_app.repositories.AccountRepository;
import com.banking_app.banking_app.repositories.TransactionRepository;
import com.banking_app.banking_app.security.interfaces.IAuthenticationFacade;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {
    @Mock
    TransactionRepository transactionRepository;
    @Mock
    AccountRepository accountRepository;
    @Mock
    IAuthenticationFacade authenticationFacade;

    @InjectMocks
    TransactionService transactionService;

    @DisplayName("Withdraw insufficient balance throws exception")
    @Test
    void withdraw_insufficientBalance_throwsException() {
        Account acc = Account
            .builder()
                .id(1L)
                    .balance(new BigDecimal("0"))
                .user(User.builder().email("user").build())
                .build();

        when(authenticationFacade.getUsername()).thenReturn("user");

        when(accountRepository.findByIdAndUser_Email(1L, "user")).thenReturn(Optional.of(acc));

        assertThrows(InsufficientBalance.class, () -> transactionService.withdraw(1L, new BigDecimal("100")));
    }

    @DisplayName("Transfer insufficient balance throws exception")
    @Test
    void transfer_insufficientBalance_throwsException() {
        Account sender = Account
            .builder()
            .id(1L)
            .balance(new BigDecimal("0"))
            .user(User.builder().email("user").build())
            .build(),

            receiver = Account
                .builder()
                .id(2L)
                .balance(new BigDecimal("0"))
                .user(User.builder().email("user2").build())
                .build();

        when(authenticationFacade.getUsername()).thenReturn("user");

        when(accountRepository.findByIdAndUser_Email(1L, "user")).thenReturn(Optional.of(sender));
        when(accountRepository.findById(2L)).thenReturn(Optional.of(receiver));

        assertThrows(InsufficientBalance.class, () -> transactionService.transfer(1L, 2L, new BigDecimal("100")));
    }

    @DisplayName("Withdraw negative amount throws exception")
    @Test
    void withdraw_negativeAmount_throwsException() {
        assertThrows(InvalidWithdrawAmount.class, () -> transactionService.withdraw(1L, new BigDecimal("-1")));
    }

    @DisplayName("Deposit valid amount saves transaction")
    @Test
    void deposit_validAmount_savesTransaction() {
        Account acc = Account
            .builder()
            .id(1L)
            .balance(new BigDecimal("0"))
            .user(User.builder().email("user").build())
            .build();

        when(authenticationFacade.getUsername()).thenReturn("user");

        when(accountRepository.findByIdAndUser_Email(1L, "user")).thenReturn(Optional.of(acc));
        when(transactionRepository.save(any())).thenReturn(Transaction
            .builder()
            .amount(new BigDecimal("100"))
            .build());


        transactionService.deposit(1L, new BigDecimal("100"));

        assertEquals(new BigDecimal("100"), acc.getBalance());

        verify(transactionRepository).save(any());
        verify(accountRepository).save(acc);
    }

    @DisplayName("Transfer to same account exception")
    @Test
    void transfer_sameAccount_throwsException() {
        assertThrows(SameAccountTransferException.class, () -> transactionService.transfer(1L, 1L, new BigDecimal("1")));
    }
}
