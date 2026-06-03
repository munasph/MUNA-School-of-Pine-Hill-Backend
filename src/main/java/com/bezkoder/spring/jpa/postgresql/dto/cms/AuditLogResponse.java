package com.bezkoder.spring.jpa.postgresql.dto.cms;

import java.time.Instant;

public class AuditLogResponse {
	private Long id;
	private String action;
	private String entityType;
	private String entityId;
	private String actorEmail;
	private String details;
	private Instant createdAt;

	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }
	public String getAction() { return action; }
	public void setAction(String action) { this.action = action; }
	public String getEntityType() { return entityType; }
	public void setEntityType(String entityType) { this.entityType = entityType; }
	public String getEntityId() { return entityId; }
	public void setEntityId(String entityId) { this.entityId = entityId; }
	public String getActorEmail() { return actorEmail; }
	public void setActorEmail(String actorEmail) { this.actorEmail = actorEmail; }
	public String getDetails() { return details; }
	public void setDetails(String details) { this.details = details; }
	public Instant getCreatedAt() { return createdAt; }
	public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
}
