package com.bezkoder.spring.jpa.postgresql.dto.seo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class PageSeoRequest {

	@NotBlank
	@Size(max = 100)
	private String pageKey;

	private String title;

	private String description;

	private String ogImageUrl;

	public String getPageKey() { return pageKey; }
	public void setPageKey(String pageKey) { this.pageKey = pageKey; }
	public String getTitle() { return title; }
	public void setTitle(String title) { this.title = title; }
	public String getDescription() { return description; }
	public void setDescription(String description) { this.description = description; }
	public String getOgImageUrl() { return ogImageUrl; }
	public void setOgImageUrl(String ogImageUrl) { this.ogImageUrl = ogImageUrl; }
}