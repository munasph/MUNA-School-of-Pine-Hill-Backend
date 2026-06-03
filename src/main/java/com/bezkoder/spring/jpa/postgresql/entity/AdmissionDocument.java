package com.bezkoder.spring.jpa.postgresql.entity;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "admission_documents")
public class AdmissionDocument {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "application_id", nullable = false)
	private Long applicationId;

	@Column(name = "doc_type", length = 100)
	private String docType;

	@Column(name = "file_name", nullable = false, length = 300)
	private String fileName;

	@Column(name = "file_url", nullable = false, length = 500)
	private String fileUrl;

	@Column(name = "uploaded_at", nullable = false)
	private Instant uploadedAt;

	@PrePersist
	void onCreate() {
		uploadedAt = Instant.now();
	}

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
