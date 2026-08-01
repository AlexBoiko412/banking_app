package com.banking_app.banking_app.repositories;

import com.banking_app.banking_app.entities.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findAllBySenderIdOrReceiverId(Long senderId, Long receiverId);

    @Query(value = """
        SELECT t.* FROM transactions t
        LEFT JOIN accounts s ON s.id = t.sender_id
        LEFT JOIN users su ON su.id = s.user_id
        LEFT JOIN accounts r ON r.id = t.receiver_id
        LEFT JOIN users ru ON ru.id = r.user_id
        WHERE (t.sender_id = :accountId AND su.email = :email)
           OR (t.receiver_id = :accountId AND ru.email = :email)
        ORDER BY t.fulfilled_timestamp DESC
        """, nativeQuery = true)
    List<Transaction> findTransactionHistory(
        @Param("accountId") Long accountId,
        @Param("email") String email
    );

    List<Transaction> findAllBySenderId(Long senderId);

    List<Transaction> findAllByReceiverId(Long receiverId);

    List<Transaction> findAllBySenderIdOrReceiverId(
        Long senderId,
        Long receiverId,
        Pageable pageable
    );

    @Query(value = """
        SELECT t.* FROM transactions t
        LEFT JOIN accounts s ON s.id = t.sender_id
        LEFT JOIN users su ON su.id = s.user_id
        LEFT JOIN accounts r ON r.id = t.receiver_id
        LEFT JOIN users ru ON ru.id = r.user_id
        WHERE (t.sender_id = :accountId AND su.email = :email)
           OR (t.receiver_id = :accountId AND ru.email = :email)
        ORDER BY t.fulfilled_timestamp DESC
        """,
        countQuery = """
        SELECT t.* FROM transactions t
        LEFT JOIN accounts s ON s.id = t.sender_id
        LEFT JOIN users su ON su.id = s.user_id
        LEFT JOIN accounts r ON r.id = t.receiver_id
        LEFT JOIN users ru ON ru.id = r.user_id
        WHERE (t.sender_id = :accountId AND su.email = :email)
           OR (t.receiver_id = :accountId AND ru.email = :email)
        ORDER BY t.fulfilled_timestamp DESC
        """, nativeQuery = true)
    Page<Transaction> findTransactionHistoryPaginated(
        @Param("accountId") Long accountId,
        @Param("email") String email,
        Pageable pageable
    );

    List<Transaction> findAllBySenderId(
        Long senderId,
        Pageable pageable
    );

    List<Transaction> findAllByReceiverId(
        Long receiverId,
        Pageable pageable
    );
}
