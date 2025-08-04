package com.example.monolithbank.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.monolithbank.dto.UserDTO;
import com.example.monolithbank.model.User;
import com.example.monolithbank.repo.UserRepository;

@Service
public class UserService {
	
	//Interface object Reference
	private UserRepository userRepository;

	//Constructor Injection
	public UserService(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}
	
	
	public void register(User user) {
		userRepository.save(user);
	}
	
	public boolean login(UserDTO userDTO) {
		boolean status =false;
		Optional<User> returnedUser =  userRepository.findByEmail(userDTO.getEmail());
		if (returnedUser.isPresent()) {
			if (returnedUser.get().getPassword().equals(userDTO.getPassword()))
					status=true;			
		} 
		return status;
	}

}
