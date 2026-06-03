package com.bezkoder.spring.jpa.postgresql.dto.cms;

import java.time.Instant;

public class AdmissionNoteResponse {
	private Long id;
	private Long applicationId;
	private String body;
	private String authorEmail;
	private Instant createdAt;

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
