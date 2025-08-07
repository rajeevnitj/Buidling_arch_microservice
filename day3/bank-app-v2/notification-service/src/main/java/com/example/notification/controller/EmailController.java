package com.example.notification.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.notification.dto.EmailDto;
import com.example.notification.service.EmailService;

@RestController
@RequestMapping("/api/email/send")
public class EmailController {

	private EmailService emailService;

	public EmailController(EmailService emailService) {
		super();
		this.emailService = emailService;
	}

	@PostMapping
	public ResponseEntity<String> sendSimpleMail(@RequestBody EmailDto request) {
		try {
			if (request.getAttachment() != null && !request.getAttachment().isEmpty()) {
				emailService.sendEmailWithAttachment(request);
			} else {
				emailService.sendEmail(request);
			}
			return ResponseEntity.ok("Email sent successfully");
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("Failed to send email: " + e.getMessage());
		}
	}

}
