package com.bezkoder.spring.jpa.postgresql.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import com.bezkoder.spring.jpa.postgresql.service.SchoolDocumentService;
import org.springframework.transaction.annotation.Transactional;

import com.bezkoder.spring.jpa.postgresql.dto.document.DocumentRequest;
import com.bezkoder.spring.jpa.postgresql.dto.document.DocumentResponse;
import com.bezkoder.spring.jpa.postgresql.entity.SchoolDocument;
import com.bezkoder.spring.jpa.postgresql.exception.ResourceNotFoundException;
import com.bezkoder.spring.jpa.postgresql.repository.SchoolDocumentRepository;
import com.bezkoder.spring.jpa.postgresql.entity.enums.CmsPublishStatus;

@Service
@Transactional(readOnly = true)
public class SchoolDocumentServiceImpl implements SchoolDocumentService {

	private final SchoolDocumentRepository repository;

	public SchoolDocumentServiceImpl(SchoolDocumentRepository repository) {
		this.repository = repository;
	}

	@Override
	public List<DocumentResponse> findAll() {
		return repository.findAllByOrderByCreatedAtDesc().stream().map(this::toResponse).toList();
	}

	@Override
	public List<DocumentResponse> findPublished() {
		return repository.findAllByOrderByCreatedAtDesc().stream()
				.filter(e -> e.getStatus() == CmsPublishStatus.PUBLISHED)
				.map(this::toResponse)
				.toList();
	}

	@Override
	public DocumentResponse findById(Long id) {
		return toResponse(findOrThrow(id));
	}

	@Override
	@Transactional
	public DocumentResponse create(DocumentRequest request) {
		SchoolDocument entity = new SchoolDocument();
		applyRequest(entity, request);
		return toResponse(repository.save(entity));
	}

	@Override
	@Transactional
	public DocumentResponse update(Long id, DocumentRequest request) {
		SchoolDocument entity = findOrThrow(id);
		applyRequest(entity, request);
		return toResponse(repository.save(entity));
	}

	@Override
	@Transactional
	public void delete(Long id) {
		if (!repository.existsById(id)) {
			throw new ResourceNotFoundException("Document not found with id: " + id);
		}
		repository.deleteById(id);
	}

	private SchoolDocument findOrThrow(Long id) {
		return repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Document not found with id: " + id));
	}

	private void applyRequest(SchoolDocument entity, DocumentRequest request) {
		entity.setTitle(request.getTitle());
		entity.setDescription(request.getDescription());
		entity.setCategory(request.getCategory());
		entity.setFileUrl(request.getFileUrl());
		entity.setFileName(request.getFileName());
		entity.setSortOrder(request.getSortOrder());
		entity.setStatus(request.getStatus());
	}

	private DocumentResponse toResponse(SchoolDocument entity) {
		DocumentResponse r = new DocumentResponse();
		r.setId(entity.getId());
		r.setTitle(entity.getTitle());
		r.setDescription(entity.getDescription());
		r.setCategory(entity.getCategory());
		r.setFileUrl(entity.getFileUrl());
		r.setFileName(entity.getFileName());
		r.setSortOrder(entity.getSortOrder());
		r.setStatus(entity.getStatus());
		r.setCreatedAt(entity.getCreatedAt());
		r.setUpdatedAt(entity.getUpdatedAt());
		return r;
	}
}
