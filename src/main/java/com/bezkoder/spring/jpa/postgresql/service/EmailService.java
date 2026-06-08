package com.bezkoder.spring.jpa.postgresql.service;

public interface EmailService {

	void sendStaffInvite(String toEmail, String setPasswordUrl);

	void sendStaffSignupReceived(String toEmail, String fullName);

	void sendStaffApproved(String toEmail, String fullName);

	void sendStaffRejected(String toEmail, String fullName);

	void sendPasswordReset(String toEmail, String resetUrl);

	void sendPlain(String toEmail, String subject, String body);
}
