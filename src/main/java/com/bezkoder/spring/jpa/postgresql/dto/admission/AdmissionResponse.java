package com.bezkoder.spring.jpa.postgresql.dto.admission;

import java.time.Instant;
import java.time.LocalDate;

import com.bezkoder.spring.jpa.postgresql.entity.enums.ApplicationStatus;

public class AdmissionResponse {

	private Long id;
	private String applicationId;
	private String fullName;
	private LocalDate dob;
	private String classApplying;
	private String gender;
	private String parentName;
	private String parentPhone;
	private ApplicationStatus status;
	private Instant submittedAt;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public LocalDate getDob() {
		return dob;
	}

	public void setDob(LocalDate dob) {
		this.dob = dob;
	}

	public String getClassApplying() {
		return classApplying;
	}

	public void setClassApplying(String classApplying) {
		this.classApplying = classApplying;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getParentPhone() {
		return parentPhone;
	}

	public void setParentPhone(String parentPhone) {
		this.parentPhone = parentPhone;
	}

	public ApplicationStatus getStatus() {
		return status;
	}

	public void setStatus(ApplicationStatus status) {
		this.status = status;
	}

	public Instant getSubmittedAt() {
		return submittedAt;
	}

	public void setSubmittedAt(Instant submittedAt) {
		this.submittedAt = submittedAt;
	}
}
