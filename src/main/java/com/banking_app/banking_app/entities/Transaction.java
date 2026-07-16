package com.banking_app.banking_app.entities;


import com.banking_app.banking_app.enums.TransactionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Entity
@Table(name = "transactions")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Enumerated(EnumType.STRING)
    TransactionType transactionType;

    LocalDateTime bookedTimestamp;
    LocalDateTime fullfilledTimestamp;

    BigDecimal amount;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    Account sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    Account receiver;
}
