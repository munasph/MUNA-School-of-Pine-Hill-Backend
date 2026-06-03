package com.bezkoder.spring.jpa.postgresql.entity;

import java.time.Instant;

import com.bezkoder.spring.jpa.postgresql.entity.enums.CmsPublishStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

/** Scaffold entity — `email_campaigns` table. */
@Entity
@Table(name = "email_campaigns")
public class EmailCampaign {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

@Column(name = "subject", length = 300, nullable = false)
	private String subject;

@Column(name = "body", nullable = false)
	private String body;

	@Enumerated(EnumType.STRING)
@Column(name = "status", length = 20, nullable = false)
	private CmsPublishStatus status = CmsPublishStatus.DRAFT;

@Column(name = "sent_at")
	private Instant sentAt;

	@Column(name = "created_at", nullable = false)
	private Instant createdAt;

	@Column(name = "updated_at", nullable = false)
	private Instant updatedAt;

	@PrePersist
	void onCreate() {
		Instant now = Instant.now();
		createdAt = now;
		updatedAt = now;
	}

	@PreUpdate
	void onUpdate() {
		updatedAt = Instant.now();
	}

	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }
	public String getSubject() { return subject; }
	public void setSubject(String subject) { this.subject = subject; }
	public String getBody() { return body; }
	public void setBody(String body) { this.body = body; }
	public CmsPublishStatus getStatus() { return status; }
	public void setStatus(CmsPublishStatus status) { this.status = status; }
	public Instant getSentAt() { return sentAt; }
	public void setSentAt(Instant sentAt) { this.sentAt = sentAt; }
	public Instant getCreatedAt() { return createdAt; }
	public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
	public Instant getUpdatedAt() { return updatedAt; }
	public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}