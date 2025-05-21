package com.jb.banking_system_application.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jb.banking_system_application.entity.Account;
import com.jb.banking_system_application.entity.Transaction;
import com.jb.banking_system_application.repository.AccountRepository;
import com.jb.banking_system_application.repository.TransactionRepository;
import com.jb.banking_system_application.service.BankingService;

@Service
public class BankingServiceImpl implements BankingService{

    @Autowired
    private AccountRepository accountRepo;
    @Autowired
    private TransactionRepository transactionRepo;


    public Account createAccount(String accountholderName, String accountType) 
    {
        String accountNumber = UUID.randomUUID().toString();
        Account acc = new Account(accountNumber, accountholderName ,accountType, 0);
        return accountRepo.save(acc);
    }

    public Optional<Account> findAccount(String accountNumber) {
        return accountRepo.findByAccountNumber(accountNumber);
    }

    public Account deposit(String accountNumber, double amount) {
        Account acc = accountRepo.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        acc.setBalance(acc.getBalance() + amount);
        accountRepo.save(acc);

        transactionRepo.save(new Transaction(accountNumber, "DEPOSIT", amount));
        return acc;
    }

    public Account withdraw(String accountNumber, double amount) {
        Account acc = accountRepo.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        if (acc.getBalance() < amount) {
            throw new RuntimeException("Insufficient balance");
        }

        acc.setBalance(acc.getBalance() - amount);
        accountRepo.save(acc);

        transactionRepo.save(new Transaction(accountNumber, "WITHDRAWAL", amount));
        return acc;
    }

    public double getBalance(String accountNumber) {
        return accountRepo.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found"))
                .getBalance();
    }

    // Admin methods
    public List<Account> getAllAccounts() {
        return accountRepo.findAll();
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepo.findAll();
    }

}