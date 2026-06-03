package com.bezkoder.spring.jpa.postgresql.dto.cms;

import java.time.Instant;

public class AdmissionDocumentResponse {
	private Long id;
	private Long applicationId;
	private String docType;
	private String fileName;
	private String fileUrl;
	private Instant uploadedAt;

	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }
	public Long getApplicationId() { return applicationId; }
	public void setApplicationId(Long applicationId) { this.applicationId = applicationId; }
	public String getDocType() { return docType; }
	public void setDocType(String docType) { this.docType = docType; }
	public String getFileName() { return fileName; }
	public void setFileName(String fileName) { this.fileName = fileName; }
	public String getFileUrl() { return fileUrl; }
	public void setFileUrl(String fileUrl) { this.fileUrl = fileUrl; }
	public Instant getUploadedAt() { return uploadedAt; }
	public void setUploadedAt(Instant uploadedAt) { this.uploadedAt = uploadedAt; }
}
