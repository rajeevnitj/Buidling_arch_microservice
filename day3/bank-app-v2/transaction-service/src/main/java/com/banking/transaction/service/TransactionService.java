package com.banking.transaction.service;

import com.banking.transaction.dto.TransactionDto;
import com.banking.transaction.entity.Transaction;
import com.banking.transaction.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionService {
    
    @Autowired
    private TransactionRepository transactionRepository;
    
    public TransactionDto deposit(String accountNumber, BigDecimal amount, String description) {
        Transaction transaction = new Transaction();
        transaction.setFromAccount(accountNumber);
        transaction.setAmount(amount);
        transaction.setType(Transaction.TransactionType.DEPOSIT);
        transaction.setDescription(description);
        transaction.setStatus(Transaction.TransactionStatus.COMPLETED);
        
        Transaction saved = transactionRepository.save(transaction);
        return convertToDto(saved);
    }
    
    public TransactionDto withdraw(String accountNumber, BigDecimal amount, String description) {
        Transaction transaction = new Transaction();
        transaction.setFromAccount(accountNumber);
        transaction.setAmount(amount);
        transaction.setType(Transaction.TransactionType.WITHDRAWAL);
        transaction.setDescription(description);
        transaction.setStatus(Transaction.TransactionStatus.COMPLETED);
        
        Transaction saved = transactionRepository.save(transaction);
        return convertToDto(saved);
    }
    
    public TransactionDto transfer(String fromAccount, String toAccount, BigDecimal amount, String description) {
        Transaction transaction = new Transaction();
        transaction.setFromAccount(fromAccount);
        transaction.setToAccount(toAccount);
        transaction.setAmount(amount);
        transaction.setType(Transaction.TransactionType.TRANSFER);
        transaction.setDescription(description);
        transaction.setStatus(Transaction.TransactionStatus.COMPLETED);
        
        Transaction saved = transactionRepository.save(transaction);
        return convertToDto(saved);
    }
    
    public List<TransactionDto> getAllTransactions() {
        return transactionRepository.findAll().stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());
    }
    
    public List<TransactionDto> getTransactionsByAccount(String accountNumber) {
        return transactionRepository.findByFromAccountOrToAccount(accountNumber, accountNumber).stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());
    }
    
    private TransactionDto convertToDto(Transaction transaction) {
        TransactionDto dto = new TransactionDto();
        dto.setId(transaction.getId());
        dto.setFromAccount(transaction.getFromAccount());
        dto.setToAccount(transaction.getToAccount());
        dto.setAmount(transaction.getAmount());
        dto.setType(transaction.getType().toString());
        dto.setStatus(transaction.getStatus().toString());
        dto.setDescription(transaction.getDescription());
        dto.setCreatedAt(transaction.getCreatedAt());
        return dto;
    }
}