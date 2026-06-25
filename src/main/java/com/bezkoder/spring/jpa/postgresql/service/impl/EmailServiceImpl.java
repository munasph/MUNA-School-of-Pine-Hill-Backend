package com.bezkoder.spring.jpa.postgresql.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.bezkoder.spring.jpa.postgresql.service.EmailService;

@Service
public class EmailServiceImpl implements EmailService {

	private static final Logger log = LoggerFactory.getLogger(EmailServiceImpl.class);

	private final JavaMailSender mailSender;
	private final boolean mailEnabled;
	private final String fromAddress;

	public EmailServiceImpl(
			JavaMailSender mailSender,
			@Value("${app.mail.enabled:false}") boolean mailEnabled,
			@Value("${app.mail.from:noreply@munasph.org}") String fromAddress) {
		this.mailSender = mailSender;
		this.mailEnabled = mailEnabled;
		this.fromAddress = fromAddress;
	}

	@Override
	public void sendStaffInvite(String toEmail, String setPasswordUrl) {
		send(
				toEmail,
				"You are invited to MUNA School admin portal",
				"You have been invited to join the MUNA School admin portal.\n\n"
						+ "Set your password here:\n" + setPasswordUrl + "\n\n"
						+ "This link expires in 48 hours.");
	}

	@Override
	public void sendStaffSignupReceived(String toEmail, String fullName) {
		send(
				toEmail,
				"Staff access request received",
				"Hello " + fullName + ",\n\n"
						+ "We received your request for staff access to the MUNA School admin portal. "
						+ "A super admin will review your request and email you when it is approved.");
	}

	@Override
	public void sendStaffApproved(String toEmail, String fullName) {
		send(
				toEmail,
				"Your staff account is approved",
				"Hello " + fullName + ",\n\n"
						+ "Your staff account has been approved. You can now log in to the admin portal.");
	}

	@Override
	public void sendStaffRejected(String toEmail, String fullName) {
		send(
				toEmail,
				"Staff access request update",
				"Hello " + fullName + ",\n\n"
						+ "We are unable to approve your staff access request at this time. "
						+ "Please contact the school office if you have questions.");
	}

	@Override
	public void sendPasswordReset(String toEmail, String resetUrl) {
		send(
				toEmail,
				"Reset your admin password",
				"Use the link below to reset your admin password:\n\n"
						+ resetUrl + "\n\n"
						+ "This link expires in 1 hour.");
	}

	@Override
	public void sendPasswordResetEmail(String toEmail, String fullName, String resetUrl) {
		send(
				toEmail,
				"Reset your portal password",
				"Hello " + fullName + ",\n\n"
						+ "Use the link below to reset your password:\n\n"
						+ resetUrl + "\n\n"
						+ "This link expires in 1 hour.");
	}

	@Override
	public void sendMagicLinkEmail(String toEmail, String fullName, String magicLinkUrl) {
		send(
				toEmail,
				"Your portal sign-in link",
				"Hello " + fullName + ",\n\n"
						+ "Use the link below to sign in:\n\n"
						+ magicLinkUrl + "\n\n"
						+ "This link expires in 15 minutes.");
	}

	@Override
	public void sendVerificationEmail(String toEmail, String fullName, String verifyUrl) {
		send(
				toEmail,
				"Verify your portal email",
				"Hello " + fullName + ",\n\n"
						+ "Please verify your email address:\n\n"
						+ verifyUrl);
	}

	@Override
	public void sendPlain(String toEmail, String subject, String body) {
		send(toEmail, subject, body);
	}

	private void send(String to, String subject, String body) {
		if (!mailEnabled) {
			log.info("[mail disabled] To: {} | Subject: {} | Body:\n{}", to, subject, body);
			return;
		}

		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(fromAddress);
		message.setTo(to);
		message.setSubject(subject);
		message.setText(body);
		mailSender.send(message);
	}
}
