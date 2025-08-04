package com.example.monolithbank.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.monolithbank.model.User;
//DAO Layer - Data Access Object (Helps to do CRUD Operation on the Entity Bean class)
public interface UserRepository extends JpaRepository<User, Integer>{
	//jpa repo - -findAll(), findById(int id) -- two types of read operation
	//jpo repo -- 3 types of modify operation -- insert, update, delete
	//save(), update(), deleteById()
	
	public  Optional<User> findByEmail(String email);
}
