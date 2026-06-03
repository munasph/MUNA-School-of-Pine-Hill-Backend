package com.bezkoder.spring.jpa.postgresql.dto.gallery;

import com.bezkoder.spring.jpa.postgresql.entity.enums.CmsPublishStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class GalleryItemRequest {

	@NotBlank
	@Size(max = 300)
	private String title;

	private String caption;

	@NotBlank
	@Size(max = 500)
	private String imageUrl;

	private String album;

	private int sortOrder;

	private CmsPublishStatus status;

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
}