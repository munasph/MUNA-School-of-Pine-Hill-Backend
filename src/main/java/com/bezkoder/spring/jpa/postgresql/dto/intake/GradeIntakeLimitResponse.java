package com.bezkoder.spring.jpa.postgresql.dto.intake;

import java.time.Instant;

public class GradeIntakeLimitResponse {

	private Long id;
	private String gradeKey;
	private String academicYear;
	private Integer maxApplications;
	private boolean waitlistEnabled;
	private Instant createdAt;
	private Instant updatedAt;

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