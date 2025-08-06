package com.banking.account.service;

import com.banking.account.dto.AccountDto;
import com.banking.account.entity.Account;
import com.banking.account.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccountService {
    
    @Autowired
    private AccountRepository accountRepository;
    
    public AccountDto createAccount(Long customerId, Account.AccountType accountType) {
        Account account = new Account();
        account.setCustomerId(customerId);
        account.setAccountType(accountType);
        account.setBalance(BigDecimal.ZERO);
        account.setAccountNumber("ACC" + String.format("%06d", System.currentTimeMillis() % 1000000));
        
        Account saved = accountRepository.save(account);
        return convertToDto(saved);
    }
    
    public Optional<AccountDto> getAccountById(Long id) {
        return accountRepository.findById(id).map(this::convertToDto);
    }
    
    public List<AccountDto> getAllAccounts() {
        return accountRepository.findAll().stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());
    }
    
    public List<AccountDto> getAccountsByCustomerId(Long customerId) {
        return accountRepository.findByCustomerId(customerId).stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());
    }
    
    public AccountDto updateBalance(String accountNumber, BigDecimal amount) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
            .orElseThrow(() -> new RuntimeException("Account not found"));
        
        account.setBalance(account.getBalance().add(amount));
        Account updated = accountRepository.save(account);
        return convertToDto(updated);
    }
    
    private AccountDto convertToDto(Account account) {
        AccountDto dto = new AccountDto();
        dto.setId(account.getId());
        dto.setAccountNumber(account.getAccountNumber());
        dto.setAccountType(account.getAccountType().toString());
        dto.setBalance(account.getBalance());
        dto.setCustomerId(account.getCustomerId());
        dto.setCreatedAt(account.getCreatedAt());
        return dto;
    }
}