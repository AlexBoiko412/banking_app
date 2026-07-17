package com.banking_app.banking_app.services;

import com.banking_app.banking_app.entities.Account;
import com.banking_app.banking_app.entities.Transaction;
import com.banking_app.banking_app.enums.TransactionType;
import com.banking_app.banking_app.exceptions.*;
import com.banking_app.banking_app.repositories.AccountRepository;
import com.banking_app.banking_app.repositories.TransactionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    @Transactional
    public Transaction transfer(Long senderId, Long receiverId, BigDecimal amount) {
        LocalDateTime bookedTime = LocalDateTime.now();

        if (senderId.equals(receiverId))
            throw new SameAccountTransferException(senderId);
        if(amount.compareTo(BigDecimal.ZERO) <= 0)
            throw new InvalidDepositAmount(amount);

        Account
                sender = accountRepository
                    .findById(senderId)
                    .orElseThrow(() -> new AccountNotFoundException(senderId)),

                receiver = accountRepository
                    .findById(receiverId)
                    .orElseThrow(() -> new AccountNotFoundException(receiverId));

        if(sender.getBalance().compareTo(amount) < 0)
            throw new InsufficientBalance(sender.getBalance(), amount);

        sender.setBalance(sender.getBalance().subtract(amount));
        receiver.setBalance(receiver.getBalance().add(amount));

        accountRepository.save(sender);
        accountRepository.save(receiver);

        Transaction transaction = Transaction.builder()
                .transactionType(TransactionType.TRANSFER)
                .amount(amount)
                .bookedTimestamp(bookedTime)
                .fullfilledTimestamp(LocalDateTime.now())
                .receiver(receiver)
                .sender(sender)
                .build();

        flagLargeTransaction(transaction);

        return transactionRepository.save(transaction);
    }

    public List<Transaction> getTransactionHistory(Long id) {
        return transactionRepository.findAllBySenderIdOrReceiverIdOrderByFullfilledTimestampDesc(id, id);
    }

    @Transactional
    public Transaction deposit(Long accountId, BigDecimal amount) {
        validateSufficientAmount(amount);

        LocalDateTime bookedTime = LocalDateTime.now();

        Account account = accountRepository
                .findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException(accountId));

        account.setBalance(account.getBalance().add(amount));

        accountRepository.save(account);

        Transaction transaction = Transaction.builder()
                .transactionType(TransactionType.DEPOSIT)
                .amount(amount)
                .bookedTimestamp(bookedTime)
                .fullfilledTimestamp(LocalDateTime.now())
                .sender(account)
                .build();

        return transactionRepository.save(transaction);
    }

    @Transactional
    public Transaction withdraw(Long accountId, BigDecimal amount) {
        validateSufficientAmount(amount);

        LocalDateTime bookedTime = LocalDateTime.now();

        Account account = accountRepository
                .findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException(accountId));

        validateSufficientWithdrawBalance(account.getBalance(), amount);

        account.setBalance(account.getBalance().subtract(amount));

        accountRepository.save(account);

        Transaction transaction = Transaction.builder()
                .transactionType(TransactionType.WITHDRAWAL)
                .amount(amount)
                .bookedTimestamp(bookedTime)
                .fullfilledTimestamp(LocalDateTime.now())
                .sender(account)
                .build();

        return transactionRepository.save(transaction);
    }

    private void validateSufficientAmount(BigDecimal amount) {
        if(amount.compareTo(BigDecimal.ZERO) <= 0)
            throw new InvalidWithdrawAmount(amount);
    }

    private void validateSufficientWithdrawBalance(BigDecimal balance, BigDecimal amount) {
        if(balance.compareTo(amount) < 0)
            throw new InsufficientBalance(balance, amount);
    }

    private void flagLargeTransaction(Transaction t) {
        if(t.getAmount().compareTo(new BigDecimal(10000)) <= 0) return;

        if (t.getTransactionType() == TransactionType.TRANSFER) {
            log.warn("Large {} detected: amount={}, txId={}, senderId={}, receiverId={}, userId={}, at={}",
                    t.getTransactionType(), t.getAmount(), t.getId(),
                    t.getSender().getId(), t.getReceiver().getId(),
                    t.getSender().getUser().getId(), t.getFullfilledTimestamp());
        } else {
            log.warn("Large {} detected: amount={}, txId={}, accountId={}, userId={}, at={}",
                    t.getTransactionType(), t.getAmount(), t.getId(),
                    t.getSender().getId(), t.getSender().getUser().getId(),
                    t.getFullfilledTimestamp());
        }
    }
}
