package com.bezkoder.spring.jpa.postgresql.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import com.bezkoder.spring.jpa.postgresql.service.FaqItemService;
import org.springframework.transaction.annotation.Transactional;

import com.bezkoder.spring.jpa.postgresql.dto.faq.FaqItemRequest;
import com.bezkoder.spring.jpa.postgresql.dto.faq.FaqItemResponse;
import com.bezkoder.spring.jpa.postgresql.entity.FaqItem;
import com.bezkoder.spring.jpa.postgresql.exception.ResourceNotFoundException;
import com.bezkoder.spring.jpa.postgresql.repository.FaqItemRepository;
import com.bezkoder.spring.jpa.postgresql.entity.enums.CmsPublishStatus;

@Service
@Transactional(readOnly = true)
public class FaqItemServiceImpl implements FaqItemService {

	private final FaqItemRepository repository;

	public FaqItemServiceImpl(FaqItemRepository repository) {
		this.repository = repository;
	}

	@Override
	public List<FaqItemResponse> findAll() {
		return repository.findAllByOrderByCreatedAtDesc().stream().map(this::toResponse).toList();
	}

	@Override
	public List<FaqItemResponse> findPublished() {
		return repository.findAllByOrderByCreatedAtDesc().stream()
				.filter(e -> e.getStatus() == CmsPublishStatus.PUBLISHED)
				.map(this::toResponse)
				.toList();
	}

	@Override
	public FaqItemResponse findById(Long id) {
		return toResponse(findOrThrow(id));
	}

	@Override
	@Transactional
	public FaqItemResponse create(FaqItemRequest request) {
		FaqItem entity = new FaqItem();
		applyRequest(entity, request);
		return toResponse(repository.save(entity));
	}

	@Override
	@Transactional
	public FaqItemResponse update(Long id, FaqItemRequest request) {
		FaqItem entity = findOrThrow(id);
		applyRequest(entity, request);
		return toResponse(repository.save(entity));
	}

	@Override
	@Transactional
	public void delete(Long id) {
		if (!repository.existsById(id)) {
			throw new ResourceNotFoundException("FaqItem not found with id: " + id);
		}
		repository.deleteById(id);
	}

	private FaqItem findOrThrow(Long id) {
		return repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("FaqItem not found with id: " + id));
	}

	private void applyRequest(FaqItem entity, FaqItemRequest request) {
		entity.setQuestion(request.getQuestion());
		entity.setAnswer(request.getAnswer());
		entity.setCategory(request.getCategory());
		entity.setSortOrder(request.getSortOrder());
		entity.setStatus(request.getStatus());
	}

	private FaqItemResponse toResponse(FaqItem entity) {
		FaqItemResponse r = new FaqItemResponse();
		r.setId(entity.getId());
		r.setQuestion(entity.getQuestion());
		r.setAnswer(entity.getAnswer());
		r.setCategory(entity.getCategory());
		r.setSortOrder(entity.getSortOrder());
		r.setStatus(entity.getStatus());
		r.setCreatedAt(entity.getCreatedAt());
		r.setUpdatedAt(entity.getUpdatedAt());
		return r;
	}
}
