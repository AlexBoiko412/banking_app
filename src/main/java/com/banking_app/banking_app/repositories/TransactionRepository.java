package com.banking_app.banking_app.repositories;

import com.banking_app.banking_app.entities.Account;
import com.banking_app.banking_app.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findAllBySenderIdOrReceiverId(Long senderId, Long receiverId);

    List<Transaction> findAllBySenderIdOrReceiverIdOrderByFullfilledTimestampDesc(Long senderId, Long receiverId);

    List<Transaction> findAllBySenderId(Long senderId);

    List<Transaction> findAllByReceiverId(Long receiverId);
}
