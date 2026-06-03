package com.bezkoder.spring.jpa.postgresql.dto.seo;

import java.time.Instant;

public class PageSeoResponse {

	private Long id;
	private String pageKey;
	private String title;
	private String description;
	private String ogImageUrl;
	private Instant updatedAt;

	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }
	public String getPageKey() { return pageKey; }
	public void setPageKey(String pageKey) { this.pageKey = pageKey; }
	public String getTitle() { return title; }
	public void setTitle(String title) { this.title = title; }
	public String getDescription() { return description; }
	public void setDescription(String description) { this.description = description; }
	public String getOgImageUrl() { return ogImageUrl; }
	public void setOgImageUrl(String ogImageUrl) { this.ogImageUrl = ogImageUrl; }
	public Instant getUpdatedAt() { return updatedAt; }
	public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}