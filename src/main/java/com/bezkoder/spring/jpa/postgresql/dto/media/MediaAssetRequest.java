package com.bezkoder.spring.jpa.postgresql.dto.media;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class MediaAssetRequest {

	@NotBlank
	@Size(max = 300)
	private String fileName;

	@NotBlank
	@Size(max = 500)
	private String url;

	private String mimeType;

	private Long sizeBytes;

	private String altText;

	public String getFileName() { return fileName; }
	public void setFileName(String fileName) { this.fileName = fileName; }
	public String getUrl() { return url; }
	public void setUrl(String url) { this.url = url; }
	public String getMimeType() { return mimeType; }
	public void setMimeType(String mimeType) { this.mimeType = mimeType; }
	public Long getSizeBytes() { return sizeBytes; }
	public void setSizeBytes(Long sizeBytes) { this.sizeBytes = sizeBytes; }
	public String getAltText() { return altText; }
	public void setAltText(String altText) { this.altText = altText; }
}