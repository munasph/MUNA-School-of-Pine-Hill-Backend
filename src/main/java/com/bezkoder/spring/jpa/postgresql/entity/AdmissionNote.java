package com.bezkoder.spring.jpa.postgresql.entity;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "admission_notes")
public class AdmissionNote {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "application_id", nullable = false)
	private Long applicationId;

	@Column(nullable = false, columnDefinition = "TEXT")
	private String body;

	@Column(name = "author_email", length = 200)
	private String authorEmail;

	@Column(name = "created_at", nullable = false)
	private Instant createdAt;

	@PrePersist
	void onCreate() {
		createdAt = Instant.now();
	}

	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }
	public Long getApplicationId() { return applicationId; }
	public void setApplicationId(Long applicationId) { this.applicationId = applicationId; }
	public String getBody() { return body; }
	public void setBody(String body) { this.body = body; }
	public String getAuthorEmail() { return authorEmail; }
	public void setAuthorEmail(String authorEmail) { this.authorEmail = authorEmail; }
	public Instant getCreatedAt() { return createdAt; }
	public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
}
