package com.bezkoder.spring.jpa.postgresql.dto.document;

import com.bezkoder.spring.jpa.postgresql.entity.enums.CmsPublishStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class DocumentRequest {

	@NotBlank
	@Size(max = 300)
	private String title;

	private String description;

	private String category;

	@NotBlank
	@Size(max = 500)
	private String fileUrl;

	private String fileName;

	private int sortOrder;

	private CmsPublishStatus status;

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
}