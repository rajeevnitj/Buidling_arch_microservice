package com.example.monolithbank.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;

import com.example.monolithbank.model.Account;

//@Repository -- optional 
public interface AccountRepository extends JpaRepository<Account, Integer>{

	List<Account> findByUserId(int id);
}
