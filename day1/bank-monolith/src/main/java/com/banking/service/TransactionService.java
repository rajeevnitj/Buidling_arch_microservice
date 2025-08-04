package com.banking.service;

import com.banking.dto.TransactionDto;
import com.banking.entity.Account;
import com.banking.entity.Transaction;
import com.banking.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountService accountService;

    public Page<Transaction> getAllTransactions(Pageable pageable) {
        return transactionRepository.findAll(pageable);
    }

    public Page<Transaction> getTransactionsByAccountId(Long accountId, Pageable pageable) {
        return transactionRepository.findByAccountId(accountId, pageable);
    }

    public Page<Transaction> getTransactionsByCustomerId(Long customerId, Pageable pageable) {
        return transactionRepository.findByCustomerId(customerId, pageable);
    }

    public Transaction getTransactionById(Long id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found with id: " + id));
    }

    @Transactional
    public Transaction createTransaction(TransactionDto transactionDto) {
        Account account = accountService.getAccountById(transactionDto.getAccountId());
        
        // Validate transaction based on type
        if (transactionDto.getType() == Transaction.TransactionType.WITHDRAWAL) {
            if (account.getBalance().compareTo(transactionDto.getAmount()) < 0) {
                throw new RuntimeException("Insufficient balance for withdrawal");
            }
            account.setBalance(account.getBalance().subtract(transactionDto.getAmount()));
        } else if (transactionDto.getType() == Transaction.TransactionType.DEPOSIT) {
            account.setBalance(account.getBalance().add(transactionDto.getAmount()));
        }
        
        Transaction transaction = new Transaction(
                transactionDto.getType(),
                transactionDto.getAmount(),
                transactionDto.getDescription(),
                account
        );
        
        return transactionRepository.save(transaction);
    }
}