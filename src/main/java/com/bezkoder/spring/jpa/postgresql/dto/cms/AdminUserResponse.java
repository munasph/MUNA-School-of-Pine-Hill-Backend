package com.bezkoder.spring.jpa.postgresql.dto.cms;

import java.time.Instant;

import com.bezkoder.spring.jpa.postgresql.entity.enums.AdminUserRole;

public class AdminUserResponse {
	private Long id;
	private String email;
	private String displayName;
	private AdminUserRole role;
	private boolean active;
	private Instant lastLoginAt;
	private Instant createdAt;

	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }
	public String getEmail() { return email; }
	public void setEmail(String email) { this.email = email; }
	public String getDisplayName() { return displayName; }
	public void setDisplayName(String displayName) { this.displayName = displayName; }
	public AdminUserRole getRole() { return role; }
	public void setRole(AdminUserRole role) { this.role = role; }
	public boolean isActive() { return active; }
	public void setActive(boolean active) { this.active = active; }
	public Instant getLastLoginAt() { return lastLoginAt; }
	public void setLastLoginAt(Instant lastLoginAt) { this.lastLoginAt = lastLoginAt; }
	public Instant getCreatedAt() { return createdAt; }
	public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
}
