package com.banking_app.banking_app.controllers;


import com.banking_app.banking_app.dtos.AccountResponse;
import com.banking_app.banking_app.dtos.CreateAccountRequest;
import com.banking_app.banking_app.entities.Account;
import com.banking_app.banking_app.services.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @GetMapping("/{id}/balance")
    public ResponseEntity<BigDecimal> getAccountBalance(@PathVariable Long id) {
        BigDecimal balance = accountService.getBalance(id);
        return ResponseEntity.ok(balance);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AccountResponse>> getAccounts(@PathVariable Long userId) {
        List<Account> accounts = accountService.getAccountsByUserId(userId);

        return ResponseEntity.ok(accounts.stream().map(this::toAccountResponse).toList()
        );
    }

    @PostMapping()
    public ResponseEntity<AccountResponse> createAccount(@RequestBody CreateAccountRequest request) {
        Account account = accountService.createAccount(request.getUserId(), request.getType());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(toAccountResponse(account));
    }

    private AccountResponse toAccountResponse(Account account) {
        return AccountResponse.builder()
                .id(account.getId())
                .balance(account.getBalance())
                .type(account.getType())
                .userId(account.getUser().getId())
                .build();
    }

}
