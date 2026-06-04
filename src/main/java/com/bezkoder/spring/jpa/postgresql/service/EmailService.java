package com.bezkoder.spring.jpa.postgresql.service;

public interface EmailService {

	void sendVerificationEmail(String to, String fullName, String verifyLink);

	void sendPasswordResetEmail(String to, String fullName, String resetLink);

	void sendMagicLinkEmail(String to, String fullName, String loginLink);
}
