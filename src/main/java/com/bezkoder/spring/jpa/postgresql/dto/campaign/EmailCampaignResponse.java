package com.bezkoder.spring.jpa.postgresql.dto.campaign;

import java.time.Instant;

import com.bezkoder.spring.jpa.postgresql.entity.enums.CmsPublishStatus;

public class EmailCampaignResponse {

	private Long id;
	private String subject;
	private String body;
	private CmsPublishStatus status;
	private Instant sentAt;
	private Instant createdAt;
	private Instant updatedAt;

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