package com.banking.transaction.controller;

import com.banking.transaction.dto.TransactionDto;
import com.banking.transaction.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/transactions")
@SecurityRequirement(name = "bearerAuth")
public class TransactionController {
    
    @Autowired
    private TransactionService transactionService;
    
    @PostMapping("/deposit")
    @Operation(summary = "Deposit money to account")
    public ResponseEntity<TransactionDto> deposit(@RequestBody Map<String, Object> request) {
        String accountNumber = request.get("accountNumber").toString();
        BigDecimal amount = new BigDecimal(request.get("amount").toString());
        String description = request.get("description").toString();
        
        TransactionDto transaction = transactionService.deposit(accountNumber, amount, description);
        return ResponseEntity.ok(transaction);
    }
    
    @PostMapping("/withdraw")
    @Operation(summary = "Withdraw money from account")
    public ResponseEntity<TransactionDto> withdraw(@RequestBody Map<String, Object> request) {
        String accountNumber = request.get("accountNumber").toString();
        BigDecimal amount = new BigDecimal(request.get("amount").toString());
        String description = request.get("description").toString();
        
        TransactionDto transaction = transactionService.withdraw(accountNumber, amount, description);
        return ResponseEntity.ok(transaction);
    }
    
    @PostMapping("/transfer")
    @Operation(summary = "Transfer money between accounts")
    public ResponseEntity<TransactionDto> transfer(@RequestBody Map<String, Object> request) {
        String fromAccount = request.get("fromAccount").toString();
        String toAccount = request.get("toAccount").toString();
        BigDecimal amount = new BigDecimal(request.get("amount").toString());
        String description = request.get("description").toString();
        
        TransactionDto transaction = transactionService.transfer(fromAccount, toAccount, amount, description);
        return ResponseEntity.ok(transaction);
    }
    
    @GetMapping
    @Operation(summary = "Get all transactions")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<TransactionDto>> getAllTransactions() {
        List<TransactionDto> transactions = transactionService.getAllTransactions();
        return ResponseEntity.ok(transactions);
    }
    
    @GetMapping("/account/{accountNumber}")
    @Operation(summary = "Get transactions by account number")
    public ResponseEntity<List<TransactionDto>> getTransactionsByAccount(@PathVariable String accountNumber) {
        List<TransactionDto> transactions = transactionService.getTransactionsByAccount(accountNumber);
        return ResponseEntity.ok(transactions);
    }
}