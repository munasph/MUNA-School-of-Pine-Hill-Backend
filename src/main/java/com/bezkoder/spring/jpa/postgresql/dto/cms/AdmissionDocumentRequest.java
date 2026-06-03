package com.bezkoder.spring.jpa.postgresql.dto.cms;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class AdmissionDocumentRequest {
	@Size(max = 100)
	private String docType;
	@NotBlank @Size(max = 300)
	private String fileName;
	@NotBlank @Size(max = 500)
	private String fileUrl;

	public String getDocType() { return docType; }
	public void setDocType(String docType) { this.docType = docType; }
	public String getFileName() { return fileName; }
	public void setFileName(String fileName) { this.fileName = fileName; }
	public String getFileUrl() { return fileUrl; }
	public void setFileUrl(String fileUrl) { this.fileUrl = fileUrl; }
}
