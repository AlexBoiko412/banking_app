package com.banking_app.banking_app.entities;

import com.banking_app.banking_app.enums.AccountType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;




@Entity
@Table(name = "accounts")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    BigDecimal balance;

    @Enumerated(EnumType.STRING)
    AccountType type;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    @OneToMany(mappedBy = "sender")
    List<Transaction> sentTransactions;

    @OneToMany(mappedBy = "receiver")
    List<Transaction> receiverTransactions;
}
