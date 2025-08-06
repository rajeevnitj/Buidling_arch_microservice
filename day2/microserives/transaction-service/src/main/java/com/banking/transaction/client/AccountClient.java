package com.banking.transaction.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.math.BigDecimal;
import java.util.Map;

@FeignClient(name = "account-service")
public interface AccountClient {
    
    @GetMapping("/api/accounts/number/{accountNumber}")
    AccountDto getAccountByNumber(@PathVariable String accountNumber);
    
    @PutMapping("/api/accounts/{accountNumber}/balance")
    AccountDto updateBalance(@PathVariable String accountNumber, @RequestBody Map<String, BigDecimal> request);
    
    class AccountDto {
        private Long id;
        private String accountNumber;
        private Long customerId;
        private BigDecimal balance;
        
        // Getters and Setters
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        
        public String getAccountNumber() { return accountNumber; }
        public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }
        
        public Long getCustomerId() { return customerId; }
        public void setCustomerId(Long customerId) { this.customerId = customerId; }
        
        public BigDecimal getBalance() { return balance; }
        public void setBalance(BigDecimal balance) { this.balance = balance; }
    }
}