package com.example.demo.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailServices {

	@Autowired
	private JavaMailSender javamailsender;

	public void sendEmail(String to, String subject, String text) {
		MimeMessage message = javamailsender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		try {
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(text, true);
			javamailsender.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
		
		}
	}

	public void sendPasswordResetEmail(String email, String resetLink) {
		String subject = "Password Reset";
		String text = "Click the following link to reset your password: " + resetLink;
		sendEmail(email, subject, text);
	}
}