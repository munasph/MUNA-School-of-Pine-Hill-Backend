package com.bezkoder.spring.jpa.postgresql.dto.cms;

import com.bezkoder.spring.jpa.postgresql.entity.enums.AdminUserRole;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class AdminUserRequest {

	@NotBlank @Email @Size(max = 200)
	private String email;

	@Size(min = 8, max = 100)
	private String password;

	@Size(max = 200)
	private String displayName;

	private AdminUserRole role = AdminUserRole.EDITOR;
	private boolean active = true;

	public String getEmail() { return email; }
	public void setEmail(String email) { this.email = email; }
	public String getPassword() { return password; }
	public void setPassword(String password) { this.password = password; }
	public String getDisplayName() { return displayName; }
	public void setDisplayName(String displayName) { this.displayName = displayName; }
	public AdminUserRole getRole() { return role; }
	public void setRole(AdminUserRole role) { this.role = role; }
	public boolean isActive() { return active; }
	public void setActive(boolean active) { this.active = active; }
}
