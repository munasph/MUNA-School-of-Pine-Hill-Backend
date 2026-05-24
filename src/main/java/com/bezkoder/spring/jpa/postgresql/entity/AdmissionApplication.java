package com.bezkoder.spring.jpa.postgresql.entity;

import java.time.Instant;
import java.time.LocalDate;

import com.bezkoder.spring.jpa.postgresql.entity.enums.ApplicationStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

/** JPA entity — maps to the {@code admission_applications} table. */
@Entity
@Table(name = "admission_applications")
public class AdmissionApplication {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "application_id", nullable = false, unique = true, length = 64)
	private String applicationId;

	@Column(name = "full_name", nullable = false, length = 200)
	private String fullName;

	@Column(nullable = false)
	private LocalDate dob;

	@Column(name = "class_applying", nullable = false, length = 100)
	private String classApplying;

	@Column(nullable = false, length = 20)
	private String gender;

	@Column(name = "parent_name", nullable = false, length = 200)
	private String parentName;

	@Column(name = "parent_phone", nullable = false, length = 30)
	private String parentPhone;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private ApplicationStatus status = ApplicationStatus.PENDING;

	@Column(name = "submitted_at", nullable = false)
	private Instant submittedAt;

	@PrePersist
	void onCreate() {
		if (submittedAt == null) {
			submittedAt = Instant.now();
		}
		if (status == null) {
			status = ApplicationStatus.PENDING;
		}
	}

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
