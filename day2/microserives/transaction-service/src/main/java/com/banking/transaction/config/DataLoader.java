package com.banking.transaction.config;

import com.banking.transaction.entity.Transaction;
import com.banking.transaction.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public void run(String... args) throws Exception {
        if (transactionRepository.count() == 0) {
            for (int i = 1; i <= 20; i++) {
                Transaction transaction = new Transaction();
                transaction.setType(i % 2 == 0 ? Transaction.TransactionType.DEPOSIT : Transaction.TransactionType.WITHDRAWAL);
                transaction.setAmount(new BigDecimal(50 + (i * 25)));
                transaction.setDescription("Sample transaction " + i);
                transaction.setFromAccount("ACC" + String.format("%06d", ((i - 1) % 20 + 1)));
                transaction.setStatus(Transaction.TransactionStatus.COMPLETED);
                transaction.setCreatedAt(LocalDateTime.now().minusDays(i));
                transactionRepository.save(transaction);
            }
        }
    }
}