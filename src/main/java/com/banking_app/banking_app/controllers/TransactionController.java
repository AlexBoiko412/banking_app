package com.banking_app.banking_app.controllers;

import com.banking_app.banking_app.dtos.DepositWithdrawRequest;
import com.banking_app.banking_app.dtos.TransactionResponse;
import com.banking_app.banking_app.dtos.TransferRequest;
import com.banking_app.banking_app.entities.Transaction;
import com.banking_app.banking_app.services.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @GetMapping("/account/{accountId}")
    public ResponseEntity<List<TransactionResponse>> getTransactions(@PathVariable Long accountId) {
        List<Transaction> transactions = transactionService.getTransactionHistory(accountId);

        return ResponseEntity.ok(
                transactions.stream().map(this::toTransactionResponse).toList()
        );
    }


    @PostMapping("/deposit")
    public ResponseEntity<TransactionResponse> deposit(@RequestBody DepositWithdrawRequest request) {
        Transaction transaction = transactionService.deposit(request.getAccountId(), request.getAmount());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(toTransactionResponse(transaction));
    }

    @PostMapping("/withdraw")
    public ResponseEntity<TransactionResponse> withdraw(@RequestBody DepositWithdrawRequest request) {
        Transaction transaction = transactionService.withdraw(request.getAccountId(), request.getAmount());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(toTransactionResponse(transaction));
    }

    @PostMapping("/transfer")
    public ResponseEntity<TransactionResponse> transfer(@RequestBody TransferRequest request) {
        Transaction transaction = transactionService.transfer(
                request.getSenderId(),
                request.getReceiverId(),
                request.getAmount()
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(toTransactionResponse(transaction));
    }

    private TransactionResponse toTransactionResponse(Transaction t) {
        return TransactionResponse
                .builder()
                .id(t.getId())
                .senderId(t.getSender().getId())
                .receiverId(t.getReceiver() != null ? t.getReceiver().getId() : null)
                .fulfilledAt(t.getFullfilledTimestamp())
                .amount(t.getAmount())
                .type(t.getTransactionType())
                .build();
    }
}
