package com.bezkoder.spring.jpa.postgresql.entity;

import java.time.Instant;

import com.bezkoder.spring.jpa.postgresql.entity.enums.CmsPublishStatus;

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

/** Scaffold entity — `faculty_members` table. */
@Entity
@Table(name = "faculty_members")
public class FacultyMember {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

@Column(name = "name", length = 200, nullable = false)
	private String name;

@Column(name = "role_title", length = 200)
	private String roleTitle;

@Column(name = "department", length = 200)
	private String department;

@Column(name = "email", length = 200)
	private String email;

@Column(name = "phone", length = 50)
	private String phone;

@Column(name = "bio", columnDefinition = "TEXT")
	private String bio;

@Column(name = "image_url", length = 500)
	private String imageUrl;

@Column(name = "sort_order")
	private int sortOrder;

	@Enumerated(EnumType.STRING)
@Column(name = "status", length = 20, nullable = false)
	private CmsPublishStatus status = CmsPublishStatus.DRAFT;

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