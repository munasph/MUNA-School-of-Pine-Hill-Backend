package com.bezkoder.spring.jpa.postgresql.entity;

import java.time.Instant;

import com.bezkoder.spring.jpa.postgresql.entity.enums.AdminAccountStatus;
import com.bezkoder.spring.jpa.postgresql.entity.enums.AdminApprovalStatus;
import com.bezkoder.spring.jpa.postgresql.entity.enums.AdminUserRole;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name = "admin_users")
public class AdminUser {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true, length = 200)
	private String email;

	@Column(name = "password_hash", nullable = false, length = 255)
	private String passwordHash;

	@Column(name = "display_name", length = 200)
	private String displayName;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private AdminUserRole role = AdminUserRole.EDITOR;

	@Column(nullable = false)
	private boolean active = true;

	@Enumerated(EnumType.STRING)
	@Column(name = "approval_status", nullable = false, length = 20)
	private AdminApprovalStatus approvalStatus = AdminApprovalStatus.APPROVED;

	@Enumerated(EnumType.STRING)
	@Column(name = "account_status", nullable = false, length = 20)
	private AdminAccountStatus accountStatus = AdminAccountStatus.ACTIVE;

	@Column(name = "invited_by")
	private Long invitedBy;

	@Column(name = "approved_by")
	private Long approvedBy;

	@Column(name = "approved_at")
	private Instant approvedAt;

	@Column(name = "invite_token_hash", length = 128)
	private String inviteTokenHash;

	@Column(name = "invite_expires_at")
	private Instant inviteExpiresAt;

	@Column(name = "failed_login_attempts", nullable = false)
	private int failedLoginAttempts = 0;

	@Column(name = "lockout_until")
	private Instant lockoutUntil;

	@Column(name = "last_login_at")
	private Instant lastLoginAt;

	@Column(name = "created_at", nullable = false)
	private Instant createdAt;

	@Column(name = "updated_at", nullable = false)
	private Instant updatedAt;

	@PrePersist
	void onCreate() {
		Instant now = Instant.now();
		createdAt = now;
		updatedAt = now;
	}

	@PreUpdate
	void onUpdate() {
		updatedAt = Instant.now();
	}

	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }
	public String getEmail() { return email; }
	public void setEmail(String email) { this.email = email; }
	public String getPasswordHash() { return passwordHash; }
	public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
	public String getDisplayName() { return displayName; }
	public void setDisplayName(String displayName) { this.displayName = displayName; }
	public AdminUserRole getRole() { return role; }
	public void setRole(AdminUserRole role) { this.role = role; }
	public boolean isActive() { return active; }
	public void setActive(boolean active) { this.active = active; }
	public AdminApprovalStatus getApprovalStatus() { return approvalStatus; }
	public void setApprovalStatus(AdminApprovalStatus approvalStatus) { this.approvalStatus = approvalStatus; }
	public AdminAccountStatus getAccountStatus() { return accountStatus; }
	public void setAccountStatus(AdminAccountStatus accountStatus) { this.accountStatus = accountStatus; }
	public Long getInvitedBy() { return invitedBy; }
	public void setInvitedBy(Long invitedBy) { this.invitedBy = invitedBy; }
	public Long getApprovedBy() { return approvedBy; }
	public void setApprovedBy(Long approvedBy) { this.approvedBy = approvedBy; }
	public Instant getApprovedAt() { return approvedAt; }
	public void setApprovedAt(Instant approvedAt) { this.approvedAt = approvedAt; }
	public String getInviteTokenHash() { return inviteTokenHash; }
	public void setInviteTokenHash(String inviteTokenHash) { this.inviteTokenHash = inviteTokenHash; }
	public Instant getInviteExpiresAt() { return inviteExpiresAt; }
	public void setInviteExpiresAt(Instant inviteExpiresAt) { this.inviteExpiresAt = inviteExpiresAt; }
	public int getFailedLoginAttempts() { return failedLoginAttempts; }
	public void setFailedLoginAttempts(int failedLoginAttempts) { this.failedLoginAttempts = failedLoginAttempts; }
	public Instant getLockoutUntil() { return lockoutUntil; }
	public void setLockoutUntil(Instant lockoutUntil) { this.lockoutUntil = lockoutUntil; }
	public Instant getLastLoginAt() { return lastLoginAt; }
	public void setLastLoginAt(Instant lastLoginAt) { this.lastLoginAt = lastLoginAt; }
	public Instant getCreatedAt() { return createdAt; }
	public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
	public Instant getUpdatedAt() { return updatedAt; }
	public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}
