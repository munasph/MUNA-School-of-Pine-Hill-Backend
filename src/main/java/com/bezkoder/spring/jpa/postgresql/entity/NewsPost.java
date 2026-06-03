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

/** Scaffold entity — `news_posts` table. */
@Entity
@Table(name = "news_posts")
public class NewsPost {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

@Column(name = "slug", length = 200, nullable = false)
	private String slug;

@Column(name = "title", length = 300, nullable = false)
	private String title;

@Column(name = "summary", length = 500)
	private String summary;

@Column(name = "body", columnDefinition = "TEXT")
	private String body;

@Column(name = "image_url", length = 500)
	private String imageUrl;

@Column(name = "author", length = 200)
	private String author;

	@Enumerated(EnumType.STRING)
@Column(name = "status", length = 20, nullable = false)
	private CmsPublishStatus status = CmsPublishStatus.DRAFT;

@Column(name = "published_at")
	private Instant publishedAt;

	@Column(name = "created_at", nullable = false)
	private Instant createdAt;

	@Column(name = "updated_at", nullable = false)
	private Instant updatedAt;

	@PrePersist
	void onCreate() {
		Instant now = Instant.now();
		createdAt = now;
		updatedAt = now;
	}

	@PreUpdate
	void onUpdate() {
		updatedAt = Instant.now();
	}

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