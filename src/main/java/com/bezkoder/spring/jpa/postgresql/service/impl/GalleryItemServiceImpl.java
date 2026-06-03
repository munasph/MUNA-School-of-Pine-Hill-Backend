package com.bezkoder.spring.jpa.postgresql.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import com.bezkoder.spring.jpa.postgresql.service.GalleryItemService;
import org.springframework.transaction.annotation.Transactional;

import com.bezkoder.spring.jpa.postgresql.dto.gallery.GalleryItemRequest;
import com.bezkoder.spring.jpa.postgresql.dto.gallery.GalleryItemResponse;
import com.bezkoder.spring.jpa.postgresql.entity.GalleryItem;
import com.bezkoder.spring.jpa.postgresql.exception.ResourceNotFoundException;
import com.bezkoder.spring.jpa.postgresql.repository.GalleryItemRepository;
import com.bezkoder.spring.jpa.postgresql.entity.enums.CmsPublishStatus;

@Service
@Transactional(readOnly = true)
public class GalleryItemServiceImpl implements GalleryItemService {

	private final GalleryItemRepository repository;

	public GalleryItemServiceImpl(GalleryItemRepository repository) {
		this.repository = repository;
	}

	@Override
	public List<GalleryItemResponse> findAll() {
		return repository.findAllByOrderByCreatedAtDesc().stream().map(this::toResponse).toList();
	}

	@Override
	public List<GalleryItemResponse> findPublished() {
		return repository.findAllByOrderByCreatedAtDesc().stream()
				.filter(e -> e.getStatus() == CmsPublishStatus.PUBLISHED)
				.map(this::toResponse)
				.toList();
	}

	@Override
	public GalleryItemResponse findById(Long id) {
		return toResponse(findOrThrow(id));
	}

	@Override
	@Transactional
	public GalleryItemResponse create(GalleryItemRequest request) {
		GalleryItem entity = new GalleryItem();
		applyRequest(entity, request);
		return toResponse(repository.save(entity));
	}

	@Override
	@Transactional
	public GalleryItemResponse update(Long id, GalleryItemRequest request) {
		GalleryItem entity = findOrThrow(id);
		applyRequest(entity, request);
		return toResponse(repository.save(entity));
	}

	@Override
	@Transactional
	public void delete(Long id) {
		if (!repository.existsById(id)) {
			throw new ResourceNotFoundException("GalleryItem not found with id: " + id);
		}
		repository.deleteById(id);
	}

	private GalleryItem findOrThrow(Long id) {
		return repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("GalleryItem not found with id: " + id));
	}

	private void applyRequest(GalleryItem entity, GalleryItemRequest request) {
		entity.setTitle(request.getTitle());
		entity.setCaption(request.getCaption());
		entity.setImageUrl(request.getImageUrl());
		entity.setAlbum(request.getAlbum());
		entity.setSortOrder(request.getSortOrder());
		entity.setStatus(request.getStatus());
	}

	private GalleryItemResponse toResponse(GalleryItem entity) {
		GalleryItemResponse r = new GalleryItemResponse();
		r.setId(entity.getId());
		r.setTitle(entity.getTitle());
		r.setCaption(entity.getCaption());
		r.setImageUrl(entity.getImageUrl());
		r.setAlbum(entity.getAlbum());
		r.setSortOrder(entity.getSortOrder());
		r.setStatus(entity.getStatus());
		r.setCreatedAt(entity.getCreatedAt());
		r.setUpdatedAt(entity.getUpdatedAt());
		return r;
	}
}
