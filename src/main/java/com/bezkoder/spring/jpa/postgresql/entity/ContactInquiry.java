package com.bezkoder.spring.jpa.postgresql.entity;

import java.time.Instant;

import com.bezkoder.spring.jpa.postgresql.entity.enums.ContactInquiryStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

/** JPA entity — maps to the {@code contact_messages} table. */
@Entity
@Table(name = "contact_messages")
public class ContactInquiry {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "message_id", nullable = false, unique = true, length = 64)
	private String messageId;

	@Column(nullable = false, length = 200)
	private String name;

	@Column(nullable = false, length = 200)
	private String email;

	@Column(nullable = false, length = 300)
	private String subject;

	@Column(nullable = false, columnDefinition = "TEXT")
	private String message;

	@Column(name = "submitted_at", nullable = false)
	private Instant submittedAt;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private ContactInquiryStatus status = ContactInquiryStatus.NEW;

	@PrePersist
	void onCreate() {
		if (submittedAt == null) {
			submittedAt = Instant.now();
		}
		if (status == null) {
			status = ContactInquiryStatus.NEW;
		}
	}

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

	public Instant getSubmittedAt() {
		return submittedAt;
	}

	public void setSubmittedAt(Instant submittedAt) {
		this.submittedAt = submittedAt;
	}

	public ContactInquiryStatus getStatus() {
		return status;
	}

	public void setStatus(ContactInquiryStatus status) {
		this.status = status;
	}
}
