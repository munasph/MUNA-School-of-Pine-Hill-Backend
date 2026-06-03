package com.bezkoder.spring.jpa.postgresql.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import com.bezkoder.spring.jpa.postgresql.service.MediaAssetService;
import org.springframework.transaction.annotation.Transactional;

import com.bezkoder.spring.jpa.postgresql.dto.media.MediaAssetRequest;
import com.bezkoder.spring.jpa.postgresql.dto.media.MediaAssetResponse;
import com.bezkoder.spring.jpa.postgresql.entity.MediaAsset;
import com.bezkoder.spring.jpa.postgresql.exception.ResourceNotFoundException;
import com.bezkoder.spring.jpa.postgresql.repository.MediaAssetRepository;

@Service
@Transactional(readOnly = true)
public class MediaAssetServiceImpl implements MediaAssetService {

	private final MediaAssetRepository repository;

	public MediaAssetServiceImpl(MediaAssetRepository repository) {
		this.repository = repository;
	}

	@Override
	public List<MediaAssetResponse> findAll() {
		return repository.findAllByOrderByIdDesc().stream().map(this::toResponse).toList();
	}

	@Override
	public List<MediaAssetResponse> findPublished() {
		return findAll();
	}

	@Override
	public MediaAssetResponse findById(Long id) {
		return toResponse(findOrThrow(id));
	}

	@Override
	@Transactional
	public MediaAssetResponse create(MediaAssetRequest request) {
		MediaAsset entity = new MediaAsset();
		applyRequest(entity, request);
		return toResponse(repository.save(entity));
	}

	@Override
	@Transactional
	public MediaAssetResponse update(Long id, MediaAssetRequest request) {
		MediaAsset entity = findOrThrow(id);
		applyRequest(entity, request);
		return toResponse(repository.save(entity));
	}

	@Override
	@Transactional
	public void delete(Long id) {
		if (!repository.existsById(id)) {
			throw new ResourceNotFoundException("MediaAsset not found with id: " + id);
		}
		repository.deleteById(id);
	}

	private MediaAsset findOrThrow(Long id) {
		return repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("MediaAsset not found with id: " + id));
	}

	private void applyRequest(MediaAsset entity, MediaAssetRequest request) {
		entity.setFileName(request.getFileName());
		entity.setUrl(request.getUrl());
		entity.setMimeType(request.getMimeType());
		entity.setSizeBytes(request.getSizeBytes());
		entity.setAltText(request.getAltText());
	}

	private MediaAssetResponse toResponse(MediaAsset entity) {
		MediaAssetResponse r = new MediaAssetResponse();
		r.setId(entity.getId());
		r.setFileName(entity.getFileName());
		r.setUrl(entity.getUrl());
		r.setMimeType(entity.getMimeType());
		r.setSizeBytes(entity.getSizeBytes());
		r.setAltText(entity.getAltText());
		return r;
	}
}
