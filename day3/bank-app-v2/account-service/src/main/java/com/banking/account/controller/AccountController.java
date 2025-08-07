package com.banking.account.controller;

import com.banking.account.dto.AccountDto;
import com.banking.account.dto.TransactionDto;
import com.banking.account.entity.Account;
import com.banking.account.service.AccountService;
import com.banking.account.service.AsyncTransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/accounts")
@SecurityRequirement(name = "bearerAuth")
public class AccountController {
    
    @Autowired
    private AccountService accountService;
    
    @Autowired
    private JobLauncher jobLauncher;
    
    @Autowired
    private Job interestJob;
    

    
    @PostMapping
    @Operation(summary = "Create a new account")
    public ResponseEntity<AccountDto> createAccount(@RequestBody Map<String, Object> request) {
        Long customerId = Long.valueOf(request.get("customerId").toString());
        Account.AccountType accountType = Account.AccountType.valueOf(request.get("accountType").toString());
        
        AccountDto account = accountService.createAccount(customerId, accountType);
        return ResponseEntity.ok(account);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get account by ID")
    public ResponseEntity<AccountDto> getAccount(@PathVariable Long id) {
        return accountService.getAccountById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping
    @Operation(summary = "Get all accounts")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<AccountDto>> getAllAccounts() {
        return ResponseEntity.ok(accountService.getAllAccounts());
    }
    
    @GetMapping("/customer/{customerId}")
    @Operation(summary = "Get accounts by customer ID")
    @PreAuthorize("hasRole('ADMIN') or #customerId == authentication.principal.customerId")
    public ResponseEntity<List<AccountDto>> getAccountsByCustomer(@PathVariable Long customerId) {
        return ResponseEntity.ok(accountService.getAccountsByCustomerId(customerId));
    }
    
    @PutMapping("/{accountNumber}/balance")
    @Operation(summary = "Update account balance")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AccountDto> updateBalance(@PathVariable String accountNumber, @RequestBody Map<String, BigDecimal> request) {
        AccountDto account = accountService.updateBalance(accountNumber, request.get("amount"));
        return ResponseEntity.ok(account);
    }
    
    @PostMapping("/interest/apply")
    @Operation(summary = "Apply quarterly interest to savings accounts")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> applyInterest() {
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();
            jobLauncher.run(interestJob, jobParameters);
            return ResponseEntity.ok("Interest application job started");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Failed to start interest job: " + e.getMessage());
        }
    }
    
    @GetMapping("/{accountNumber}/transactions")
    @Operation(summary = "Get transactions for account")
    public ResponseEntity<List<TransactionDto>> getAccountTransactions(@PathVariable String accountNumber) {
        try {
            System.out.println("Direct call to transaction service for account: " + accountNumber);
            org.springframework.web.client.RestTemplate restTemplate = new org.springframework.web.client.RestTemplate();
            org.springframework.core.ParameterizedTypeReference<List<TransactionDto>> typeRef = 
                new org.springframework.core.ParameterizedTypeReference<List<TransactionDto>>() {};
            
            org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
            String authHeader = ((jakarta.servlet.http.HttpServletRequest) 
                org.springframework.web.context.request.RequestContextHolder.currentRequestAttributes()
                .resolveReference(org.springframework.web.context.request.RequestAttributes.REFERENCE_REQUEST))
                .getHeader("Authorization");
            
            if (authHeader != null) {
                headers.set("Authorization", authHeader);
            }
            
            org.springframework.http.HttpEntity<?> entity = new org.springframework.http.HttpEntity<>(headers);
            
            List<TransactionDto> transactions = restTemplate.exchange(
                "http://localhost:8083/api/transactions/account/" + accountNumber,
                org.springframework.http.HttpMethod.GET,
                entity,
                typeRef
            ).getBody();
            
            System.out.println("Found " + (transactions != null ? transactions.size() : 0) + " transactions");
            return ResponseEntity.ok(transactions != null ? transactions : java.util.Collections.emptyList());
        } catch (Exception e) {
            System.err.println("Error fetching transactions: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.ok(java.util.Collections.emptyList());
        }
    }
}