package com.banking.account.controller;

import com.banking.account.dto.AccountDto;
import com.banking.account.entity.Account;
import com.banking.account.service.AccountService;
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
    @PreAuthorize("hasRole('ADMIN') or #customerId == authentication.principal.id")
    public ResponseEntity<List<AccountDto>> getAccountsByCustomer(@PathVariable Long customerId) {
        return ResponseEntity.ok(accountService.getAccountsByCustomerId(customerId));
    }
    
    @PutMapping("/{accountNumber}/balance")
    @Operation(summary = "Update account balance")
    public ResponseEntity<AccountDto> updateBalance(@PathVariable String accountNumber, @RequestBody Map<String, BigDecimal> request) {
        AccountDto account = accountService.updateBalance(accountNumber, request.get("amount"));
        return ResponseEntity.ok(account);
    }
    
    @PostMapping("/interest/apply")
    @Operation(summary = "Apply quarterly interest to savings accounts")
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
}