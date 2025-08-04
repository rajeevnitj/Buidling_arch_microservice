package com.example.monolithbank.controller;

import java.util.List;
import com.example.monolithbank.service.UserService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.monolithbank.model.Account;
import com.example.monolithbank.service.AccountService;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {

    private final UserService userService;

	private AccountService accountService;

	public AccountController(AccountService accountService, UserService userService) {
		super();
		this.accountService = accountService;
		this.userService = userService;
	}

	@GetMapping
	public List<Account> findAll() {
		return accountService.findAll();
	}

	@GetMapping("/{id}")
	public Account findById(@PathVariable int id) {
		return accountService.findById(id).get();
	}

	@GetMapping("/user/{userId}")
	public List<Account> findByUserId(@PathVariable int userId) {
		return accountService.findByUserId(userId);
	}

	@PostMapping
	public void save(@RequestBody Account account) {
		accountService.save(account);
	}

	@PutMapping("/{id}")
	public void update(@PathVariable int id, @RequestBody Account account) {
		accountService.update(id, account);
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable int id) {
		accountService.delete(id);
	}

}
