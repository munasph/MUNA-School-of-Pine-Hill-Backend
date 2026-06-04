package com.bezkoder.spring.jpa.postgresql.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.bezkoder.spring.jpa.postgresql.service.EmailService;

/**
 * Sends transactional email via SMTP when configured ({@code app.mail.enabled=true} and a
 * mail host is present). Otherwise it logs the link to the console so local/dev signups
 * still work end-to-end without an SMTP provider.
 */
@Service
public class EmailServiceImpl implements EmailService {

	private static final Logger log = LoggerFactory.getLogger(EmailServiceImpl.class);

	private final ObjectProvider<JavaMailSender> mailSenderProvider;
	private final boolean mailEnabled;
	private final String fromAddress;

	public EmailServiceImpl(
			ObjectProvider<JavaMailSender> mailSenderProvider,
			@Value("${app.mail.enabled:false}") boolean mailEnabled,
			@Value("${app.mail.from:no-reply@munasph.org}") String fromAddress) {
		this.mailSenderProvider = mailSenderProvider;
		this.mailEnabled = mailEnabled;
		this.fromAddress = fromAddress;
	}

	@Override
	public void sendVerificationEmail(String to, String fullName, String verifyLink) {
		String body = "Hi " + fullName + ",\n\n"
				+ "Welcome to MUNA School of Pine Hill. Please confirm your email address to activate your account:\n\n"
				+ verifyLink + "\n\n"
				+ "This link expires soon. If you did not create an account, you can ignore this email.";
		send(to, "Confirm your email", body, verifyLink);
	}

	@Override
	public void sendPasswordResetEmail(String to, String fullName, String resetLink) {
		String body = "Hi " + fullName + ",\n\n"
				+ "We received a request to reset your password. Use the link below to choose a new one:\n\n"
				+ resetLink + "\n\n"
				+ "This link expires soon. If you did not request this, no action is needed — your password stays the same.";
		send(to, "Reset your password", body, resetLink);
	}

	@Override
	public void sendMagicLinkEmail(String to, String fullName, String loginLink) {
		String body = "Hi " + fullName + ",\n\n"
				+ "Here is your secure login link for the MUNA School family portal:\n\n"
				+ loginLink + "\n\n"
				+ "This link expires shortly and can only be used once. If you did not request it, you can ignore this email.";
		send(to, "Your login link", body, loginLink);
	}

	private void send(String to, String subject, String body, String link) {
		JavaMailSender sender = mailSenderProvider.getIfAvailable();
		if (!mailEnabled || sender == null) {
			log.info("[EMAIL DISABLED] To: {} | Subject: {} | Link: {}", to, subject, link);
			return;
		}

		try {
			SimpleMailMessage message = new SimpleMailMessage();
			message.setFrom(fromAddress);
			message.setTo(to);
			message.setSubject(subject);
			message.setText(body);
			sender.send(message);
			log.info("Sent '{}' email to {}", subject, to);
		} catch (Exception ex) {
			log.error("Failed to send '{}' email to {}: {}", subject, to, ex.getMessage());
		}
	}
}
