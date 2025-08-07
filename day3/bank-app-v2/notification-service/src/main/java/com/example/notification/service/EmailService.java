package com.example.notification.service;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.example.notification.dto.EmailDto;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EmailService {

	// private Environment env;

	@Autowired // Setter Injection
	private JavaMailSender javaMailSender;

	// @Autowired // Constructor Injection - recommended
	// public EmailService(Environment env) {
	// 	super();
	// 	this.env = env;
	// }

	public void sendEmail(EmailDto emailDto) {
		SimpleMailMessage message = new SimpleMailMessage();
		log.info(this.getClass().getName() + " Send Simple Email Start!!!");
		message.setFrom("syskantechnosoft@gmail.com"); // Replace with your Gmail
		message.setTo(emailDto.getReceiver());
		message.setSubject(emailDto.getSubject());
		message.setText(emailDto.getBody());

		javaMailSender.send(message);
		log.info(this.getClass().getName() + " Send Simple Email End!!!");
	}

	public void sendEmailWithAttachment(EmailDto emailDto) {
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper;
		try {
			helper = new MimeMessageHelper(message, true);
			helper.setFrom("syskantechnosoft@gmail.com"); // Replace with your Gmail
			helper.setTo(emailDto.getReceiver());
			helper.setSubject(emailDto.getSubject());
			helper.setText(emailDto.getBody());
			String attachmentPath = emailDto.getAttachment();
			if (attachmentPath != null && !attachmentPath.isEmpty()) {
				FileSystemResource file = new FileSystemResource(new File(attachmentPath));
				helper.addAttachment(file.getFilename(), file);
			}

			javaMailSender.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
		}

		log.info("Mail Sent with Attachement");
	}
}
