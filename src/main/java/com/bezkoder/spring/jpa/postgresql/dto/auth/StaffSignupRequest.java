package com.bezkoder.spring.jpa.postgresql.dto.auth;

import com.bezkoder.spring.jpa.postgresql.entity.enums.AdminUserRole;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class StaffSignupRequest {

	@NotBlank
	@Size(max = 200)
	private String fullName;

	@NotBlank
	@Size(min = 3, max = 50)
	private String username;

	@NotBlank
	@Email
	private String email;

	@NotBlank
	@Size(min = 8, max = 100)
	private String password;

	@NotBlank
	@Size(min = 8, max = 100)
	private String confirmPassword;

	@NotNull
	private AdminUserRole role = AdminUserRole.EDITOR;

	public String getFullName() { return fullName; }
	public void setFullName(String fullName) { this.fullName = fullName; }
	public String getUsername() { return username; }
	public void setUsername(String username) { this.username = username; }
	public String getEmail() { return email; }
	public void setEmail(String email) { this.email = email; }
	public String getPassword() { return password; }
	public void setPassword(String password) { this.password = password; }
	public String getConfirmPassword() { return confirmPassword; }
	public void setConfirmPassword(String confirmPassword) { this.confirmPassword = confirmPassword; }
	public AdminUserRole getRole() { return role; }
	public void setRole(AdminUserRole role) { this.role = role; }
}
