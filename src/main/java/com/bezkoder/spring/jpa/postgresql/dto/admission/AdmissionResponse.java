package com.bezkoder.spring.jpa.postgresql.dto.admission;

import java.time.Instant;
import java.time.LocalDate;

import com.bezkoder.spring.jpa.postgresql.entity.enums.ApplicationStatus;

public class AdmissionResponse {

	private Long id;
	private String applicationId;
	private String fullName;
	private String firstName;
	private String lastName;
	private LocalDate dob;
	private String classApplying;
	private String gender;
	private String streetAddress;
	private String city;
	private String state;
	private String zip;
	private String parentName;
	private String parentPhone;
	private String parent1Email;
	private String parent2Name;
	private String parent2Phone;
	private String parent2Email;
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

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
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

	public String getStreetAddress() {
		return streetAddress;
	}

	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
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

	public String getParent1Email() {
		return parent1Email;
	}

	public void setParent1Email(String parent1Email) {
		this.parent1Email = parent1Email;
	}

	public String getParent2Name() {
		return parent2Name;
	}

	public void setParent2Name(String parent2Name) {
		this.parent2Name = parent2Name;
	}

	public String getParent2Phone() {
		return parent2Phone;
	}

	public void setParent2Phone(String parent2Phone) {
		this.parent2Phone = parent2Phone;
	}

	public String getParent2Email() {
		return parent2Email;
	}

	public void setParent2Email(String parent2Email) {
		this.parent2Email = parent2Email;
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
