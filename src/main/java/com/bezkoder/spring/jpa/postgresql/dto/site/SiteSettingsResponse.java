package com.bezkoder.spring.jpa.postgresql.dto.site;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SiteSettingsResponse {

	private String name;
	private String shortName;
	private String foundedYear;
	private String address;
	private String phone;
	private String email;
	private String officeHours;
	private String baseUrl;
	private boolean admissionsOpen;
	private boolean admissionDocumentsRequired;
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

	@JsonProperty("admissionDocumentsRequired")
	public boolean getAdmissionDocumentsRequired() {
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
