package com.example.monolithbank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.monolithbank.dto.UserDTO;
import com.example.monolithbank.model.User;
import com.example.monolithbank.service.UserService;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
	
	private UserService userService;

//	@Autowired
	public UserController(UserService userService) {
		super();
		this.userService = userService;
	}
	
	@PostMapping("/register")
	public void register(@RequestBody User user ) {
		userService.register(user);
	}
	
	@PostMapping("/login")
	public void login(@RequestBody UserDTO userDTO) {
		userService.login(userDTO);
		
	}
	

}
