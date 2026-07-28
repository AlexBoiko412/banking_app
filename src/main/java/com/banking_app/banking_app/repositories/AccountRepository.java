package com.banking_app.banking_app.repositories;

import com.banking_app.banking_app.entities.Account;
import com.banking_app.banking_app.enums.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findAllByUserId(Long userId);

    Optional<Account> findByUserIdAndType(Long userId, AccountType type);
    Optional<Account> findByIdAndUser_Email(Long id, String email);
    List<Account> findAllByUserIdAndUser_Email(Long userId, String email);
}
