package com.bezkoder.spring.jpa.postgresql.dto.intake;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class GradeIntakeLimitRequest {

	@NotBlank
	@Size(max = 100)
	private String gradeKey;

	@NotBlank
	@Size(max = 20)
	private String academicYear;

	private Integer maxApplications;

	private boolean waitlistEnabled;

	public String getGradeKey() { return gradeKey; }
	public void setGradeKey(String gradeKey) { this.gradeKey = gradeKey; }
	public String getAcademicYear() { return academicYear; }
	public void setAcademicYear(String academicYear) { this.academicYear = academicYear; }
	public Integer getMaxApplications() { return maxApplications; }
	public void setMaxApplications(Integer maxApplications) { this.maxApplications = maxApplications; }
	public boolean getWaitlistEnabled() { return waitlistEnabled; }
	public void setWaitlistEnabled(boolean waitlistEnabled) { this.waitlistEnabled = waitlistEnabled; }
}