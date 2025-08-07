package com.banking.account.service;

import com.banking.account.client.TransactionServiceClient;
import com.banking.account.dto.TransactionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class AsyncTransactionService {

    @Autowired
    private TransactionServiceClient transactionServiceClient;

    @Async
    public CompletableFuture<List<TransactionDto>> getTransactionsAsync(String accountNumber) {
        try {
            List<TransactionDto> transactions = transactionServiceClient.getTransactionsByAccountNumber(accountNumber);
            return CompletableFuture.completedFuture(transactions);
        } catch (Exception e) {
            e.printStackTrace();
            return CompletableFuture.completedFuture(java.util.Collections.emptyList());
        }
    }
}