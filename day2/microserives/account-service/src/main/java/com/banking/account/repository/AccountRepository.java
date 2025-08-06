package com.banking.account.repository;

import com.banking.account.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByAccountNumber(String accountNumber);
    List<Account> findByCustomerId(Long customerId);
    
    @Query("SELECT a FROM Account a WHERE a.accountType = 'SAVINGS' AND (a.lastInterestApplied IS NULL OR a.lastInterestApplied < :cutoffDate)")
    List<Account> findSavingsAccountsForInterest(java.time.LocalDateTime cutoffDate);
}