package com.banking_app.banking_app.repositories;

import com.banking_app.banking_app.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findAllBySenderIdOrReceiverId(Long senderId, Long receiverId);

    List<Transaction> findAllBySenderIdOrReceiverIdOrderByFulfilledTimestampDesc(Long senderId, Long receiverId);
    @Query("SELECT t FROM Transaction t " +
            "WHERE (t.sender.id = :senderId OR t.receiver.id = :receiverId)" +
            "AND (t.sender.user.email = :email OR t.receiver.user.email = :email)" +
            "ORDER BY t.fulfilledTimestamp DESC")
    List<Transaction> findAllBySenderOrReceiverAccAndOwnerEmail(@Param("senderId") Long senderId, @Param("receiverId") Long receiverId, @Param("email") String email);

    List<Transaction> findAllBySenderId(Long senderId);

    List<Transaction> findAllByReceiverId(Long receiverId);
}
