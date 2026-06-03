package com.bezkoder.spring.jpa.postgresql.dto.faculty;

import com.bezkoder.spring.jpa.postgresql.entity.enums.CmsPublishStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class FacultyMemberRequest {

	@NotBlank
	@Size(max = 200)
	private String name;

	private String roleTitle;

	private String department;

	private String email;

	private String phone;

	private String bio;

	private String imageUrl;

	private int sortOrder;

	private CmsPublishStatus status;

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
}