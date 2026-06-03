package com.bezkoder.spring.jpa.postgresql.dto.campaign;

import java.time.Instant;

import com.bezkoder.spring.jpa.postgresql.entity.enums.CmsPublishStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class EmailCampaignRequest {

	@NotBlank
	@Size(max = 300)
	private String subject;

	@NotBlank
	private String body;

	private CmsPublishStatus status;

	private Instant sentAt;

	public String getSubject() { return subject; }
	public void setSubject(String subject) { this.subject = subject; }
	public String getBody() { return body; }
	public void setBody(String body) { this.body = body; }
	public CmsPublishStatus getStatus() { return status; }
	public void setStatus(CmsPublishStatus status) { this.status = status; }
	public Instant getSentAt() { return sentAt; }
	public void setSentAt(Instant sentAt) { this.sentAt = sentAt; }
}