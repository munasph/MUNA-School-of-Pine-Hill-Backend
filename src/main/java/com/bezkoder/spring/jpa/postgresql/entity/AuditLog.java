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
@Table(name = "audit_logs")
public class AuditLog {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 50)
	private String action;

	@Column(name = "entity_type", nullable = false, length = 100)
	private String entityType;

	@Column(name = "entity_id", length = 100)
	private String entityId;

	@Column(name = "actor_email", length = 200)
	private String actorEmail;

	@Column(columnDefinition = "TEXT")
	private String details;

	@Column(name = "created_at", nullable = false)
	private Instant createdAt;

	@PrePersist
	void onCreate() {
		createdAt = Instant.now();
	}

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
