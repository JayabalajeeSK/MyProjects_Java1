package com.jb.banking_system_application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.jb.banking_system_application.entity.Account;
import com.jb.banking_system_application.service.impl.BankingServiceImpl;

@RestController
@RequestMapping("/user")
@PreAuthorize("hasRole('USER')")
public class UserController {

    @Autowired
    private BankingServiceImpl bankingService;

    @PostMapping("/createAccount")
    public ResponseEntity<Account> createAccount(@RequestParam String accountType) {
        Account account = bankingService.createAccount(accountType, accountType);
        return ResponseEntity.ok(account);
    }

    @PostMapping("/deposit")
    public ResponseEntity<Account> deposit(@RequestParam String accountNumber, @RequestParam double amount) {
        Account account = bankingService.deposit(accountNumber, amount);
        return ResponseEntity.ok(account);
    }

    @PostMapping("/withdraw")
    public ResponseEntity<Account> withdraw(@RequestParam String accountNumber, @RequestParam double amount) {
        Account account = bankingService.withdraw(accountNumber, amount);
        return ResponseEntity.ok(account);
    }

    @GetMapping("/balance")
    public ResponseEntity<Double> getBalance(@RequestParam String accountNumber) {
        double balance = bankingService.getBalance(accountNumber);
        return ResponseEntity.ok(balance);
    }
}