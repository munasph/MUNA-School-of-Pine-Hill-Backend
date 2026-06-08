package com.bezkoder.spring.jpa.postgresql.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/** JPA entity — single-row {@code site_settings} table (id is always 1). */
@Entity
@Table(name = "site_settings")
public class SiteSettings {

	public static final long SINGLETON_ID = 1L;

	@Id
	private Long id = SINGLETON_ID;

	@Column(nullable = false, length = 200)
	private String name = "MUNA School of Pine Hill";

	@Column(name = "short_name", nullable = false, length = 100)
	private String shortName = "MSPH";

	@Column(name = "founded_year", length = 10)
	private String foundedYear = "2026";

	@Column(length = 500)
	private String address = "400 Erial Rd, Pine Hill, NJ 08021";

	@Column(length = 50)
	private String phone = "856-484-6949";

	@Column(length = 200)
	private String email = "info@munasph.org";

	@Column(name = "office_hours", length = 200)
	private String officeHours = "Office Hours Placeholder";

	@Column(name = "base_url", length = 500)
	private String baseUrl = "https://munasph.org";

	@Column(name = "admissions_open", nullable = false)
	private boolean admissionsOpen = true;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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
}
