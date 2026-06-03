package com.bezkoder.spring.jpa.postgresql.dto.faculty;

import java.time.Instant;

import com.bezkoder.spring.jpa.postgresql.entity.enums.CmsPublishStatus;

public class FacultyMemberResponse {

	private Long id;
	private String name;
	private String roleTitle;
	private String department;
	private String email;
	private String phone;
	private String bio;
	private String imageUrl;
	private int sortOrder;
	private CmsPublishStatus status;
	private Instant createdAt;
	private Instant updatedAt;

	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	public String getRoleTitle() { return roleTitle; }
	public void setRoleTitle(String roleTitle) { this.roleTitle = roleTitle; }
	public String getDepartment() { return department; }
	public void setDepartment(String department) { this.department = department; }
	public String getEmail() { return email; }
	public void setEmail(String email) { this.email = email; }
	public String getPhone() { return phone; }
	public void setPhone(String phone) { this.phone = phone; }
	public String getBio() { return bio; }
	public void setBio(String bio) { this.bio = bio; }
	public String getImageUrl() { return imageUrl; }
	public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
	public int getSortOrder() { return sortOrder; }
	public void setSortOrder(int sortOrder) { this.sortOrder = sortOrder; }
	public CmsPublishStatus getStatus() { return status; }
	public void setStatus(CmsPublishStatus status) { this.status = status; }
	public Instant getCreatedAt() { return createdAt; }
	public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
	public Instant getUpdatedAt() { return updatedAt; }
	public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}