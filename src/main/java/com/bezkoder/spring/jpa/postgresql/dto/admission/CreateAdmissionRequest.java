package com.bezkoder.spring.jpa.postgresql.dto.admission;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/** Matches the Angular registration form payload. */
public class CreateAdmissionRequest {

	@NotBlank
	@Size(max = 100)
	private String firstName;

	@NotBlank
	@Size(max = 100)
	private String lastName;

	@NotBlank
	@Size(max = 20)
	private String gender;

	@NotNull
	private LocalDate dob;

	@NotBlank
	@Size(max = 200)
	private String streetAddress;

	@NotBlank
	@Size(max = 100)
	private String city;

	@NotBlank
	@Size(max = 50)
	private String state;

	@NotBlank
	@Size(max = 20)
	private String zip;

	@NotBlank
	@Size(max = 100)
	private String classApplying;

	@NotBlank
	@Size(max = 200)
	private String parent1Name;

	@NotBlank
	@Size(min = 7, max = 30)
	private String parent1Phone;

	@NotBlank
	@Email
	@Size(max = 200)
	private String parent1Email;

	@Size(max = 200)
	private String parent2Name;

	@Size(max = 30)
	private String parent2Phone;

	@Email
	@Size(max = 200)
	private String parent2Email;

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

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public LocalDate getDob() {
		return dob;
	}

	public void setDob(LocalDate dob) {
		this.dob = dob;
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

	public String getClassApplying() {
		return classApplying;
	}

	public void setClassApplying(String classApplying) {
		this.classApplying = classApplying;
	}

	public String getParent1Name() {
		return parent1Name;
	}

	public void setParent1Name(String parent1Name) {
		this.parent1Name = parent1Name;
	}

	public String getParent1Phone() {
		return parent1Phone;
	}

	public void setParent1Phone(String parent1Phone) {
		this.parent1Phone = parent1Phone;
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
}
