package com.bezkoder.spring.jpa.postgresql.dto.cms;

import java.time.Instant;

public class NotificationSettingsResponse {
	private boolean emailOnNewAdmission;
	private boolean emailOnNewContact;
	private String adminNotificationEmail;
	private Instant updatedAt;

	public boolean isEmailOnNewAdmission() { return emailOnNewAdmission; }
	public void setEmailOnNewAdmission(boolean v) { this.emailOnNewAdmission = v; }
	public boolean isEmailOnNewContact() { return emailOnNewContact; }
	public void setEmailOnNewContact(boolean v) { this.emailOnNewContact = v; }
	public String getAdminNotificationEmail() { return adminNotificationEmail; }
	public void setAdminNotificationEmail(String v) { this.adminNotificationEmail = v; }
	public Instant getUpdatedAt() { return updatedAt; }
	public void setUpdatedAt(Instant v) { this.updatedAt = v; }
}
