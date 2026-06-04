package com.bezkoder.spring.jpa.postgresql.entity;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

/**
 * Links a portal user to a student's admission record. Status starts PENDING and an
 * admin must approve before the portal user can read that student's data.
 */
@Entity
@Table(name = "parent_student_links")
public class ParentStudentLink {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "portal_user_id", nullable = false)
	private Long portalUserId;

	@Column(name = "application_id", nullable = false)
	private Long applicationId;

	@Column(nullable = false, length = 50)
	private String relationship = "PARENT";

	@Column(nullable = false, length = 20)
	private String status = "PENDING";

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
	public Long getPortalUserId() { return portalUserId; }
	public void setPortalUserId(Long portalUserId) { this.portalUserId = portalUserId; }
	public Long getApplicationId() { return applicationId; }
	public void setApplicationId(Long applicationId) { this.applicationId = applicationId; }
	public String getRelationship() { return relationship; }
	public void setRelationship(String relationship) { this.relationship = relationship; }
	public String getStatus() { return status; }
	public void setStatus(String status) { this.status = status; }
	public Instant getCreatedAt() { return createdAt; }
	public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
	public Instant getUpdatedAt() { return updatedAt; }
	public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}
