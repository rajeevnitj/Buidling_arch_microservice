package com.example.monolithbank.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.monolithbank.model.Account;
import com.example.monolithbank.repo.AccountRepository;

@Service
public class AccountService {
	
	private AccountRepository accountRepository;

	public AccountService(AccountRepository accountRepository) {
		super();
		this.accountRepository = accountRepository;
	}
	
	//Read All operation 
	public List<Account> findAll() {
		return accountRepository.findAll();
	}

	//Read by Account ID operation
	public Optional<Account> findById(int id){
		return accountRepository.findById(id);
			
	}
	
	//Read by UserID operation
	public List<Account> findByUserId(int userId){
		return accountRepository.findByUserId(userId);
	}
	
	//save - creating a new account
	public void save(Account account) {
		accountRepository.save(account);
	}
	
	//update operation 
	public void update(int id, Account account) {
		accountRepository.save(account);
	}
	
	//delete operation
	public void delete(int id) {
		accountRepository.deleteById(id);
	}
	
}
