package com.banking.controller;

import com.banking.dto.AccountDto;
import com.banking.entity.Account;
import com.banking.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accounts")
@Tag(name = "Account Management", description = "APIs for managing bank accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping
    @Operation(summary = "Get all accounts", description = "Retrieve all accounts with pagination and sorting")
    public ResponseEntity<Page<Account>> getAllAccounts(@PageableDefault(size = 20) Pageable pageable) {
        Page<Account> accounts = accountService.getAllAccounts(pageable);
        return ResponseEntity.ok(accounts);
    }

    @GetMapping("/customer/{customerId}")
    @Operation(summary = "Get accounts by customer", description = "Retrieve all accounts for a specific customer")
    public ResponseEntity<Page<Account>> getAccountsByCustomerId(
            @PathVariable Long customerId,
            @PageableDefault(size = 20) Pageable pageable) {
        Page<Account> accounts = accountService.getAccountsByCustomerId(customerId, pageable);
        return ResponseEntity.ok(accounts);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get account by ID", description = "Retrieve a specific account by its ID")
    public ResponseEntity<Account> getAccountById(@PathVariable Long id) {
        Account account = accountService.getAccountById(id);
        return ResponseEntity.ok(account);
    }

    @PostMapping
    @Operation(summary = "Create new account", description = "Create a new bank account")
    public ResponseEntity<Account> createAccount(@Valid @RequestBody AccountDto accountDto) {
        Account account = accountService.createAccount(accountDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(account);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete account", description = "Delete an account by ID")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long id) {
        accountService.deleteAccount(id);
        return ResponseEntity.noContent().build();
    }
}