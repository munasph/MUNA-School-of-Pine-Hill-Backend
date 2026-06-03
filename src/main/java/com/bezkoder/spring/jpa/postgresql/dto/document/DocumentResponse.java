package com.bezkoder.spring.jpa.postgresql.dto.document;

import java.time.Instant;

import com.bezkoder.spring.jpa.postgresql.entity.enums.CmsPublishStatus;

public class DocumentResponse {

	private Long id;
	private String title;
	private String description;
	private String category;
	private String fileUrl;
	private String fileName;
	private int sortOrder;
	private CmsPublishStatus status;
	private Instant createdAt;
	private Instant updatedAt;

	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }
	public String getTitle() { return title; }
	public void setTitle(String title) { this.title = title; }
	public String getDescription() { return description; }
	public void setDescription(String description) { this.description = description; }
	public String getCategory() { return category; }
	public void setCategory(String category) { this.category = category; }
	public String getFileUrl() { return fileUrl; }
	public void setFileUrl(String fileUrl) { this.fileUrl = fileUrl; }
	public String getFileName() { return fileName; }
	public void setFileName(String fileName) { this.fileName = fileName; }
	public int getSortOrder() { return sortOrder; }
	public void setSortOrder(int sortOrder) { this.sortOrder = sortOrder; }
	public CmsPublishStatus getStatus() { return status; }
	public void setStatus(CmsPublishStatus status) { this.status = status; }
	public Instant getCreatedAt() { return createdAt; }
	public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
	public Instant getUpdatedAt() { return updatedAt; }
	public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}