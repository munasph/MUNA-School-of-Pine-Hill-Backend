package com.bezkoder.spring.jpa.postgresql.dto.site;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class SiteSettingsRequest {

	@NotBlank
	@Size(max = 200)
	private String name;

	@NotBlank
	@Size(max = 100)
	private String shortName;

	@Size(max = 10)
	private String foundedYear;

	@Size(max = 500)
	private String address;

	@Size(max = 50)
	private String phone;

	@NotBlank
	@Email
	@Size(max = 200)
	private String email;

	@Size(max = 200)
	private String officeHours;

	@Size(max = 500)
	private String baseUrl;

	private boolean admissionsOpen = true;

	@JsonProperty("admissionDocumentsRequired")
	private boolean admissionDocumentsRequired = false;

	private List<String> admissionRequiredDocumentTypes = new ArrayList<>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getFoundedYear() {
		return foundedYear;
	}

	public void setFoundedYear(String foundedYear) {
		this.foundedYear = foundedYear;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getOfficeHours() {
		return officeHours;
	}

	public void setOfficeHours(String officeHours) {
		this.officeHours = officeHours;
	}

	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public boolean isAdmissionsOpen() {
		return admissionsOpen;
	}

	public void setAdmissionsOpen(boolean admissionsOpen) {
		this.admissionsOpen = admissionsOpen;
	}

	public boolean isAdmissionDocumentsRequired() {
		return admissionDocumentsRequired;
	}

	public void setAdmissionDocumentsRequired(boolean admissionDocumentsRequired) {
		this.admissionDocumentsRequired = admissionDocumentsRequired;
	}

	public List<String> getAdmissionRequiredDocumentTypes() {
		return admissionRequiredDocumentTypes;
	}

	public void setAdmissionRequiredDocumentTypes(List<String> admissionRequiredDocumentTypes) {
		this.admissionRequiredDocumentTypes = admissionRequiredDocumentTypes;
	}
}
