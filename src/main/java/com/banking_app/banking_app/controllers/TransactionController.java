package com.banking_app.banking_app.controllers;

import com.banking_app.banking_app.dtos.DepositWithdrawRequest;
import com.banking_app.banking_app.dtos.PaginatedTransactionsResponse;
import com.banking_app.banking_app.dtos.TransactionResponse;
import com.banking_app.banking_app.dtos.TransferRequest;
import com.banking_app.banking_app.entities.Transaction;
import com.banking_app.banking_app.services.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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

    @GetMapping("/account/{accountId}/paginated")
    public ResponseEntity<PaginatedTransactionsResponse> getTransactionsPaginated(
        @PathVariable Long accountId,
        @RequestParam int page,
        @RequestParam int pageSize
    ) {
        Page<Transaction> paginatedTransactions = transactionService.getTransactionHistoryPaginated(accountId, page, pageSize);

        return ResponseEntity.ok(
            PaginatedTransactionsResponse.builder()
                 .totalElements(paginatedTransactions.getTotalElements())
                 .totalPages(paginatedTransactions.getTotalPages())
                 .transactions(paginatedTransactions.stream().map(this::toTransactionResponse).toList())
                 .build()
        );
    }



    @PostMapping("/deposit")
    public ResponseEntity<TransactionResponse> deposit(@Valid @RequestBody DepositWithdrawRequest request) {
        Transaction transaction = transactionService.deposit(request.getAccountId(), request.getAmount());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(toTransactionResponse(transaction));
    }

    @PostMapping("/withdraw")
    public ResponseEntity<TransactionResponse> withdraw(@Valid @RequestBody DepositWithdrawRequest request) {
        Transaction transaction = transactionService.withdraw(request.getAccountId(), request.getAmount());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(toTransactionResponse(transaction));
    }

    @PostMapping("/transfer")
    public ResponseEntity<TransactionResponse> transfer(@Valid @RequestBody TransferRequest request) {
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
                .fulfilledAt(t.getFulfilledTimestamp())
                .amount(t.getAmount())
                .type(t.getTransactionType())
                .build();
    }
}
