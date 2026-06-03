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

/** Scaffold entity — `grade_intake_limits` table. */
@Entity
@Table(name = "grade_intake_limits")
public class GradeIntakeLimit {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

@Column(name = "grade_key", length = 100, nullable = false)
	private String gradeKey;

@Column(name = "academic_year", length = 20, nullable = false)
	private String academicYear;

@Column(name = "max_applications")
	private Integer maxApplications;

@Column(name = "waitlist_enabled")
	private boolean waitlistEnabled = false;

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
	public String getGradeKey() { return gradeKey; }
	public void setGradeKey(String gradeKey) { this.gradeKey = gradeKey; }
	public String getAcademicYear() { return academicYear; }
	public void setAcademicYear(String academicYear) { this.academicYear = academicYear; }
	public Integer getMaxApplications() { return maxApplications; }
	public void setMaxApplications(Integer maxApplications) { this.maxApplications = maxApplications; }
	public boolean getWaitlistEnabled() { return waitlistEnabled; }
	public void setWaitlistEnabled(boolean waitlistEnabled) { this.waitlistEnabled = waitlistEnabled; }
	public Instant getCreatedAt() { return createdAt; }
	public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
	public Instant getUpdatedAt() { return updatedAt; }
	public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}