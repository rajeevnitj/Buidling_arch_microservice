package com.banking.account.config;

import com.banking.account.entity.Account;
import com.banking.account.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public void run(String... args) throws Exception {
        if (accountRepository.count() == 0) {
            for (int i = 1; i <= 20; i++) {
                Account account = new Account();
                account.setAccountNumber("ACC" + String.format("%06d", i));
                account.setAccountType(i % 2 == 0 ? Account.AccountType.SAVINGS : Account.AccountType.CHECKING);
                account.setBalance(new BigDecimal(1000 + (i * 100)));
                account.setCustomerId((long) ((i - 1) % 20 + 1));
                accountRepository.save(account);
            }
        }
    }
}