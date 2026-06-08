package com.bezkoder.spring.jpa.postgresql.dto.staff;

import java.time.Instant;

import com.bezkoder.spring.jpa.postgresql.entity.enums.AdminApprovalStatus;
import com.bezkoder.spring.jpa.postgresql.entity.enums.AdminUserRole;

public class StaffMemberResponse {

	private Long id;
	private String email;
	private String displayName;
	private AdminUserRole role;
	private AdminApprovalStatus approvalStatus;
	private boolean active;
	private Instant createdAt;
	private Instant approvedAt;

	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }
	public String getEmail() { return email; }
	public void setEmail(String email) { this.email = email; }
	public String getDisplayName() { return displayName; }
	public void setDisplayName(String displayName) { this.displayName = displayName; }
	public AdminUserRole getRole() { return role; }
	public void setRole(AdminUserRole role) { this.role = role; }
	public AdminApprovalStatus getApprovalStatus() { return approvalStatus; }
	public void setApprovalStatus(AdminApprovalStatus approvalStatus) { this.approvalStatus = approvalStatus; }
	public boolean isActive() { return active; }
	public void setActive(boolean active) { this.active = active; }
	public Instant getCreatedAt() { return createdAt; }
	public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
	public Instant getApprovedAt() { return approvedAt; }
	public void setApprovedAt(Instant approvedAt) { this.approvedAt = approvedAt; }
}
