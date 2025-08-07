package com.banking.account.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String accountNumber;
    
    @Column(nullable = false)
    private Long customerId;
    
    @Enumerated(EnumType.STRING)
    private AccountType accountType;
    
    @Column(precision = 15, scale = 2)
    private BigDecimal balance = BigDecimal.ZERO;
    
    @Column(precision = 5, scale = 2)
    private BigDecimal interestRate = BigDecimal.ZERO;
    
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime lastInterestApplied;
    
    public enum AccountType {
        SAVINGS, CHECKING, CURRENT
    }
    
    // Constructors
    public Account() {}
    
    public Account(String accountNumber, Long customerId, AccountType accountType, BigDecimal interestRate) {
        this.accountNumber = accountNumber;
        this.customerId = customerId;
        this.accountType = accountType;
        this.interestRate = interestRate;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getAccountNumber() { return accountNumber; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }
    
    public Long getCustomerId() { return customerId; }
    public void setCustomerId(Long customerId) { this.customerId = customerId; }
    
    public AccountType getAccountType() { return accountType; }
    public void setAccountType(AccountType accountType) { this.accountType = accountType; }
    
    public BigDecimal getBalance() { return balance; }
    public void setBalance(BigDecimal balance) { this.balance = balance; }
    
    public BigDecimal getInterestRate() { return interestRate; }
    public void setInterestRate(BigDecimal interestRate) { this.interestRate = interestRate; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public LocalDateTime getLastInterestApplied() { return lastInterestApplied; }
    public void setLastInterestApplied(LocalDateTime lastInterestApplied) { this.lastInterestApplied = lastInterestApplied; }
}