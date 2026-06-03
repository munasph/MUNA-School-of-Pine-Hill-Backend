package com.bezkoder.spring.jpa.postgresql.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import com.bezkoder.spring.jpa.postgresql.service.PageSeoService;
import org.springframework.transaction.annotation.Transactional;

import com.bezkoder.spring.jpa.postgresql.dto.seo.PageSeoRequest;
import com.bezkoder.spring.jpa.postgresql.dto.seo.PageSeoResponse;
import com.bezkoder.spring.jpa.postgresql.entity.PageSeo;
import com.bezkoder.spring.jpa.postgresql.exception.ResourceNotFoundException;
import com.bezkoder.spring.jpa.postgresql.repository.PageSeoRepository;

@Service
@Transactional(readOnly = true)
public class PageSeoServiceImpl implements PageSeoService {

	private final PageSeoRepository repository;

	public PageSeoServiceImpl(PageSeoRepository repository) {
		this.repository = repository;
	}

	@Override
	public List<PageSeoResponse> findAll() {
		return repository.findAllByOrderByIdDesc().stream().map(this::toResponse).toList();
	}

	@Override
	public List<PageSeoResponse> findPublished() {
		return findAll();
	}

	@Override
	public PageSeoResponse findById(Long id) {
		return toResponse(findOrThrow(id));
	}

	@Override
	@Transactional
	public PageSeoResponse create(PageSeoRequest request) {
		PageSeo entity = new PageSeo();
		applyRequest(entity, request);
		return toResponse(repository.save(entity));
	}

	@Override
	@Transactional
	public PageSeoResponse update(Long id, PageSeoRequest request) {
		PageSeo entity = findOrThrow(id);
		applyRequest(entity, request);
		return toResponse(repository.save(entity));
	}

	@Override
	public PageSeoResponse findByPageKey(String pageKey) {
		return toResponse(repository.findByPageKey(pageKey)
				.orElseThrow(() -> new ResourceNotFoundException("SEO not found for page: " + pageKey)));
	}

	@Override
	@Transactional
	public void delete(Long id) {
		if (!repository.existsById(id)) {
			throw new ResourceNotFoundException("PageSeo not found with id: " + id);
		}
		repository.deleteById(id);
	}

	private PageSeo findOrThrow(Long id) {
		return repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("PageSeo not found with id: " + id));
	}

	private void applyRequest(PageSeo entity, PageSeoRequest request) {
		entity.setPageKey(request.getPageKey());
		entity.setTitle(request.getTitle());
		entity.setDescription(request.getDescription());
		entity.setOgImageUrl(request.getOgImageUrl());
	}

	private PageSeoResponse toResponse(PageSeo entity) {
		PageSeoResponse r = new PageSeoResponse();
		r.setId(entity.getId());
		r.setPageKey(entity.getPageKey());
		r.setTitle(entity.getTitle());
		r.setDescription(entity.getDescription());
		r.setOgImageUrl(entity.getOgImageUrl());
		r.setUpdatedAt(entity.getUpdatedAt());
		return r;
	}
}
