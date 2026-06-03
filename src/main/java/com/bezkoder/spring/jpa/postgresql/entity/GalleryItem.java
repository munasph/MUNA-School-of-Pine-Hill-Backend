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

/** Scaffold entity — `gallery_items` table. */
@Entity
@Table(name = "gallery_items")
public class GalleryItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

@Column(name = "title", length = 300, nullable = false)
	private String title;

@Column(name = "caption", length = 500)
	private String caption;

@Column(name = "image_url", length = 500, nullable = false)
	private String imageUrl;

@Column(name = "album", length = 100)
	private String album;

@Column(name = "sort_order")
	private int sortOrder;

	@Enumerated(EnumType.STRING)
@Column(name = "status", length = 20, nullable = false)
	private CmsPublishStatus status = CmsPublishStatus.DRAFT;

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
	public String getTitle() { return title; }
	public void setTitle(String title) { this.title = title; }
	public String getCaption() { return caption; }
	public void setCaption(String caption) { this.caption = caption; }
	public String getImageUrl() { return imageUrl; }
	public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
	public String getAlbum() { return album; }
	public void setAlbum(String album) { this.album = album; }
	public int getSortOrder() { return sortOrder; }
	public void setSortOrder(int sortOrder) { this.sortOrder = sortOrder; }
	public CmsPublishStatus getStatus() { return status; }
	public void setStatus(CmsPublishStatus status) { this.status = status; }
	public Instant getCreatedAt() { return createdAt; }
	public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
	public Instant getUpdatedAt() { return updatedAt; }
	public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}