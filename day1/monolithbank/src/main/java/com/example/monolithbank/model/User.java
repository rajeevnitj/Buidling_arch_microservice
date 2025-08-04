package com.example.monolithbank.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor //Construction injection (CI)
@NoArgsConstructor //Helps to do setter injection
@Entity //To make it as a Entity Bean class
@Table (name = "users") //To provide custom name for the table
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String name;
	//add unique validation 
	private String email;
	//add unique validataion 
	private long mobile;
	private String password;

}
