package com.bezkoder.spring.jpa.postgresql.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import com.bezkoder.spring.jpa.postgresql.service.InquiryReplyTemplateService;
import org.springframework.transaction.annotation.Transactional;

import com.bezkoder.spring.jpa.postgresql.dto.inquiry.InquiryReplyTemplateRequest;
import com.bezkoder.spring.jpa.postgresql.dto.inquiry.InquiryReplyTemplateResponse;
import com.bezkoder.spring.jpa.postgresql.entity.InquiryReplyTemplate;
import com.bezkoder.spring.jpa.postgresql.exception.ResourceNotFoundException;
import com.bezkoder.spring.jpa.postgresql.repository.InquiryReplyTemplateRepository;

@Service
@Transactional(readOnly = true)
public class InquiryReplyTemplateServiceImpl implements InquiryReplyTemplateService {

	private final InquiryReplyTemplateRepository repository;

	public InquiryReplyTemplateServiceImpl(InquiryReplyTemplateRepository repository) {
		this.repository = repository;
	}

	@Override
	public List<InquiryReplyTemplateResponse> findAll() {
		return repository.findAllByOrderByCreatedAtDesc().stream().map(this::toResponse).toList();
	}

	@Override
	public List<InquiryReplyTemplateResponse> findPublished() {
		return findAll();
	}

	@Override
	public InquiryReplyTemplateResponse findById(Long id) {
		return toResponse(findOrThrow(id));
	}

	@Override
	@Transactional
	public InquiryReplyTemplateResponse create(InquiryReplyTemplateRequest request) {
		InquiryReplyTemplate entity = new InquiryReplyTemplate();
		applyRequest(entity, request);
		return toResponse(repository.save(entity));
	}

	@Override
	@Transactional
	public InquiryReplyTemplateResponse update(Long id, InquiryReplyTemplateRequest request) {
		InquiryReplyTemplate entity = findOrThrow(id);
		applyRequest(entity, request);
		return toResponse(repository.save(entity));
	}

	@Override
	@Transactional
	public void delete(Long id) {
		if (!repository.existsById(id)) {
			throw new ResourceNotFoundException("InquiryReplyTemplate not found with id: " + id);
		}
		repository.deleteById(id);
	}

	private InquiryReplyTemplate findOrThrow(Long id) {
		return repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("InquiryReplyTemplate not found with id: " + id));
	}

	private void applyRequest(InquiryReplyTemplate entity, InquiryReplyTemplateRequest request) {
		entity.setName(request.getName());
		entity.setSubject(request.getSubject());
		entity.setBody(request.getBody());
	}

	private InquiryReplyTemplateResponse toResponse(InquiryReplyTemplate entity) {
		InquiryReplyTemplateResponse r = new InquiryReplyTemplateResponse();
		r.setId(entity.getId());
		r.setName(entity.getName());
		r.setSubject(entity.getSubject());
		r.setBody(entity.getBody());
		r.setCreatedAt(entity.getCreatedAt());
		r.setUpdatedAt(entity.getUpdatedAt());
		return r;
	}
}
