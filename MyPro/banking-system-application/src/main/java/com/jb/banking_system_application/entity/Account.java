package com.jb.banking_system_application.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;


@Entity
@Data
@AllArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String accountNumber;

    private String accountholderName;

    private String accountType; // Savings or Current

    private double balance;

    // Constructors, getters, setters

    public Account() {}

    public Account(String accountNumber, String accountholderName, String accountType, double balance) {
        this.accountNumber = accountNumber;
        this.accountholderName = accountholderName;
        this.accountType = accountType;
        this.balance = balance;
    }
}