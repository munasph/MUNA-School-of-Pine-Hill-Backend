package com.bezkoder.spring.jpa.postgresql.dto.portal;

import java.util.List;

public class PortalProfileResponse {

	private String email;
	private String fullName;
	private String role;
	private boolean emailVerified;
	private boolean mfaEnabled;
	private List<LinkedStudentResponse> students;

	public String getEmail() { return email; }
	public void setEmail(String email) { this.email = email; }
	public String getFullName() { return fullName; }
	public void setFullName(String fullName) { this.fullName = fullName; }
	public String getRole() { return role; }
	public void setRole(String role) { this.role = role; }
	public boolean isEmailVerified() { return emailVerified; }
	public void setEmailVerified(boolean emailVerified) { this.emailVerified = emailVerified; }
	public boolean isMfaEnabled() { return mfaEnabled; }
	public void setMfaEnabled(boolean mfaEnabled) { this.mfaEnabled = mfaEnabled; }
	public List<LinkedStudentResponse> getStudents() { return students; }
	public void setStudents(List<LinkedStudentResponse> students) { this.students = students; }
}
