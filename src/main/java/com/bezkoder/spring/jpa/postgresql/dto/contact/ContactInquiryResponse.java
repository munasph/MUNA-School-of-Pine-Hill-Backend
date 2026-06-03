package com.bezkoder.spring.jpa.postgresql.dto.contact;

import java.time.Instant;

import com.bezkoder.spring.jpa.postgresql.entity.enums.ContactInquiryStatus;

public class ContactInquiryResponse {

	private Long id;
	private String messageId;
	private String name;
	private String email;
	private String subject;
	private String message;
	private ContactInquiryStatus status;
	private Instant submittedAt;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public ContactInquiryStatus getStatus() {
		return status;
	}

	public void setStatus(ContactInquiryStatus status) {
		this.status = status;
	}

	public Instant getSubmittedAt() {
		return submittedAt;
	}

	public void setSubmittedAt(Instant submittedAt) {
		this.submittedAt = submittedAt;
	}
}
