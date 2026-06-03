package com.bezkoder.spring.jpa.postgresql.entity;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name = "notification_settings")
public class NotificationSettings {

	public static final long SINGLETON_ID = 1L;

	@Id
	private Long id = SINGLETON_ID;

	@Column(name = "email_on_new_admission", nullable = false)
	private boolean emailOnNewAdmission = true;

	@Column(name = "email_on_new_contact", nullable = false)
	private boolean emailOnNewContact = true;

	@Column(name = "admin_notification_email", length = 200)
	private String adminNotificationEmail;

	@Column(name = "updated_at", nullable = false)
	private Instant updatedAt = Instant.now();

	@PreUpdate
	void onUpdate() {
		updatedAt = Instant.now();
	}

	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }
	public boolean isEmailOnNewAdmission() { return emailOnNewAdmission; }
	public void setEmailOnNewAdmission(boolean emailOnNewAdmission) { this.emailOnNewAdmission = emailOnNewAdmission; }
	public boolean isEmailOnNewContact() { return emailOnNewContact; }
	public void setEmailOnNewContact(boolean emailOnNewContact) { this.emailOnNewContact = emailOnNewContact; }
	public String getAdminNotificationEmail() { return adminNotificationEmail; }
	public void setAdminNotificationEmail(String adminNotificationEmail) { this.adminNotificationEmail = adminNotificationEmail; }
	public Instant getUpdatedAt() { return updatedAt; }
	public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}
