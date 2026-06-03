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

/** Scaffold entity — `media_assets` table. */
@Entity
@Table(name = "media_assets")
public class MediaAsset {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

@Column(name = "file_name", length = 300, nullable = false)
	private String fileName;

@Column(name = "url", length = 500, nullable = false)
	private String url;

@Column(name = "mime_type", length = 100)
	private String mimeType;

@Column(name = "size_bytes")
	private Long sizeBytes;

@Column(name = "alt_text", length = 300)
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