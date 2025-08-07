package com.banking.account.client;

import com.banking.account.dto.TransactionDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "transaction-service", url = "http://localhost:8083", configuration = com.banking.account.config.FeignConfig.class)
public interface TransactionServiceClient {
    
    @GetMapping("/api/transactions/account/{accountNumber}")
    List<TransactionDto> getTransactionsByAccountNumber(@PathVariable("accountNumber") String accountNumber);
}