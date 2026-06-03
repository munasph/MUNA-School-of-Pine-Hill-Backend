package com.bezkoder.spring.jpa.postgresql.service;

import java.util.List;

import com.bezkoder.spring.jpa.postgresql.dto.gallery.GalleryItemRequest;
import com.bezkoder.spring.jpa.postgresql.dto.gallery.GalleryItemResponse;

public interface GalleryItemService {

	List<GalleryItemResponse> findAll();

	List<GalleryItemResponse> findPublished();

	GalleryItemResponse findById(Long id);

	GalleryItemResponse create(GalleryItemRequest request);

	GalleryItemResponse update(Long id, GalleryItemRequest request);

	void delete(Long id);
}
