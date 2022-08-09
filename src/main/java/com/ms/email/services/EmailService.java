package com.ms.email.services;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.ms.email.enums.StatusEmail;
import com.ms.email.model.Email;
import com.ms.email.repositories.EmailRepository;

@Service
public class EmailService {
	
	@Autowired
	EmailRepository emailRepository;
	
	@Autowired
	private JavaMailSender emailSender;

	@SuppressWarnings("finally")
	public Email sendEmail(Email email) {
		email.setSendDateEmail(LocalDateTime.now());
		try {
			SimpleMailMessage msg = new SimpleMailMessage();
			msg.setFrom(email.getEmailFrom());
			msg.setTo(email.getEmailTo());
			msg.setSubject(email.getSubject());
			msg.setText(email.getText());
			emailSender.send(msg);
			
			email.setStatusEmail(StatusEmail.SENT);
		} catch (Exception e) {
			email.setStatusEmail(StatusEmail.ERROR);
		} finally {
			return emailRepository.save(email);
		}
	}

}
