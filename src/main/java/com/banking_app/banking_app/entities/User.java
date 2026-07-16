package com.banking_app.banking_app.entities;


import com.banking_app.banking_app.enums.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;



@Entity
@Table(name = "users")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String email;

    String login;

    String firstName;
    String lastName;

    String address;

    String passwordHash;

    @Enumerated(EnumType.STRING)
    UserRole role;



    @OneToMany(mappedBy = "user")
    List<Account> accounts;
}
