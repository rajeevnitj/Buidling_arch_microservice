package com.banking.controller;

import com.banking.dto.TransactionDto;
import com.banking.entity.Transaction;
import com.banking.service.TransactionService;
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
@RequestMapping("/api/transactions")
@Tag(name = "Transaction Management", description = "APIs for managing transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping
    @Operation(summary = "Get all transactions", description = "Retrieve all transactions with pagination and sorting")
    public ResponseEntity<Page<Transaction>> getAllTransactions(@PageableDefault(size = 20) Pageable pageable) {
        Page<Transaction> transactions = transactionService.getAllTransactions(pageable);
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/account/{accountId}")
    @Operation(summary = "Get transactions by account", description = "Retrieve all transactions for a specific account")
    public ResponseEntity<Page<Transaction>> getTransactionsByAccountId(
            @PathVariable Long accountId,
            @PageableDefault(size = 20) Pageable pageable) {
        Page<Transaction> transactions = transactionService.getTransactionsByAccountId(accountId, pageable);
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/customer/{customerId}")
    @Operation(summary = "Get transactions by customer", description = "Retrieve all transactions for a specific customer")
    public ResponseEntity<Page<Transaction>> getTransactionsByCustomerId(
            @PathVariable Long customerId,
            @PageableDefault(size = 20) Pageable pageable) {
        Page<Transaction> transactions = transactionService.getTransactionsByCustomerId(customerId, pageable);
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get transaction by ID", description = "Retrieve a specific transaction by its ID")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable Long id) {
        Transaction transaction = transactionService.getTransactionById(id);
        return ResponseEntity.ok(transaction);
    }

    @PostMapping
    @Operation(summary = "Create new transaction", description = "Create a new transaction (deposit/withdrawal)")
    public ResponseEntity<Transaction> createTransaction(@Valid @RequestBody TransactionDto transactionDto) {
        Transaction transaction = transactionService.createTransaction(transactionDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(transaction);
    }
}