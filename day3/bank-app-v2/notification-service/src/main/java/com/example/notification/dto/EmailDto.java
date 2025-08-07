package com.example.notification.dto;

import lombok.Data;

@Data
public class EmailDto {
	
	private String receiver;
	private String subject;
	private String body;
	private String attachment;

}
