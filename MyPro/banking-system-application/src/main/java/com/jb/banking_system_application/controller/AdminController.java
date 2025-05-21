package com.jb.banking_system_application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.jb.banking_system_application.entity.Account;
import com.jb.banking_system_application.entity.Transaction;
import com.jb.banking_system_application.service.impl.BankingServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @Autowired
    private BankingServiceImpl bankingService;

    @GetMapping("/accounts")
    public List<Account> getAllAccounts() {
        return bankingService.getAllAccounts();
    }

    @GetMapping("/transactions")
    public List<Transaction> getAllTransactions() {
        return bankingService.getAllTransactions();
    }
}