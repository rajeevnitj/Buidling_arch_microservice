package com.banking.repository;

import com.banking.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    
    Page<Transaction> findByAccountId(Long accountId, Pageable pageable);
    
    @Query("SELECT t FROM Transaction t WHERE t.account.customer.id = :customerId")
    Page<Transaction> findByCustomerId(@Param("customerId") Long customerId, Pageable pageable);
}