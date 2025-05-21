package com.jb.banking_system_application.service;

import java.util.List;
import java.util.Optional;

import com.jb.banking_system_application.entity.Account;
import com.jb.banking_system_application.entity.Transaction;

public interface BankingService {

    List<Account> getAllAccounts();
    List<Transaction> getAllTransactions();

    Optional<Account> findAccount(String accountNumber);

    Account createAccount(String accountHoldername,String accountType);

    Account deposit(String accountNumber, double amount);
    Account withdraw(String accountNumber, double amount);
    double getBalance(String accountNumber);

}
