package com.bezkoder.spring.jpa.postgresql.dto.news;

import java.time.Instant;

import com.bezkoder.spring.jpa.postgresql.entity.enums.CmsPublishStatus;

public class NewsPostResponse {

	private Long id;
	private String slug;
	private String title;
	private String summary;
	private String body;
	private String imageUrl;
	private String author;
	private CmsPublishStatus status;
	private Instant publishedAt;
	private Instant createdAt;
	private Instant updatedAt;

	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }
	public String getSlug() { return slug; }
	public void setSlug(String slug) { this.slug = slug; }
	public String getTitle() { return title; }
	public void setTitle(String title) { this.title = title; }
	public String getSummary() { return summary; }
	public void setSummary(String summary) { this.summary = summary; }
	public String getBody() { return body; }
	public void setBody(String body) { this.body = body; }
	public String getImageUrl() { return imageUrl; }
	public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
	public String getAuthor() { return author; }
	public void setAuthor(String author) { this.author = author; }
	public CmsPublishStatus getStatus() { return status; }
	public void setStatus(CmsPublishStatus status) { this.status = status; }
	public Instant getPublishedAt() { return publishedAt; }
	public void setPublishedAt(Instant publishedAt) { this.publishedAt = publishedAt; }
	public Instant getCreatedAt() { return createdAt; }
	public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
	public Instant getUpdatedAt() { return updatedAt; }
	public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}