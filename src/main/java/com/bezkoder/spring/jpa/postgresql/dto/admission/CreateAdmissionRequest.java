package com.bezkoder.spring.jpa.postgresql.dto.admission;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/** Matches the Angular {@code AdmissionApplication} payload. */
public class CreateAdmissionRequest {

	@NotBlank
	@Size(max = 200)
	private String fullName;

	@NotNull
	private LocalDate dob;

	@NotBlank
	@Size(max = 100)
	private String classApplying;

	@NotBlank
	@Size(max = 20)
	private String gender;

	@NotBlank
	@Size(max = 200)
	private String parentName;

	@NotBlank
	@Size(min = 7, max = 30)
	private String parentPhone;

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
}
