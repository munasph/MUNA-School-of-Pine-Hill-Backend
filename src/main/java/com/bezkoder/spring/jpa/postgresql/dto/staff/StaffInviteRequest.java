package com.bezkoder.spring.jpa.postgresql.dto.staff;

import com.bezkoder.spring.jpa.postgresql.entity.enums.AdminUserRole;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class StaffInviteRequest {

	@NotBlank
	@Email
	private String email;

	@NotBlank
	private String displayName;

	@NotNull
	private AdminUserRole role = AdminUserRole.EDITOR;

	public String getEmail() { return email; }
	public void setEmail(String email) { this.email = email; }
	public String getDisplayName() { return displayName; }
	public void setDisplayName(String displayName) { this.displayName = displayName; }
	public AdminUserRole getRole() { return role; }
	public void setRole(AdminUserRole role) { this.role = role; }
}
