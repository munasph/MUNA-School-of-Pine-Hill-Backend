package com.bezkoder.spring.jpa.postgresql.dto.media;

import java.time.Instant;

public class MediaAssetResponse {

	private Long id;
	private String fileName;
	private String url;
	private String mimeType;
	private Long sizeBytes;
	private String altText;

	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }
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