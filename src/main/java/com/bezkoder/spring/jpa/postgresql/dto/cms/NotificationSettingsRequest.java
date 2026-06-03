package com.bezkoder.spring.jpa.postgresql.dto.cms;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public class NotificationSettingsRequest {
	private boolean emailOnNewAdmission = true;
	private boolean emailOnNewContact = true;
	@Email @Size(max = 200)
	private String adminNotificationEmail;

	public boolean isEmailOnNewAdmission() { return emailOnNewAdmission; }
	public void setEmailOnNewAdmission(boolean v) { this.emailOnNewAdmission = v; }
	public boolean isEmailOnNewContact() { return emailOnNewContact; }
	public void setEmailOnNewContact(boolean v) { this.emailOnNewContact = v; }
	public String getAdminNotificationEmail() { return adminNotificationEmail; }
	public void setAdminNotificationEmail(String v) { this.adminNotificationEmail = v; }
}
