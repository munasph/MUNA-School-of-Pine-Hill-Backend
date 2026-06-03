package com.bezkoder.spring.jpa.postgresql.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import com.bezkoder.spring.jpa.postgresql.service.NewsPostService;
import org.springframework.transaction.annotation.Transactional;

import com.bezkoder.spring.jpa.postgresql.dto.news.NewsPostRequest;
import com.bezkoder.spring.jpa.postgresql.dto.news.NewsPostResponse;
import com.bezkoder.spring.jpa.postgresql.entity.NewsPost;
import com.bezkoder.spring.jpa.postgresql.exception.ResourceNotFoundException;
import com.bezkoder.spring.jpa.postgresql.repository.NewsPostRepository;
import com.bezkoder.spring.jpa.postgresql.entity.enums.CmsPublishStatus;

@Service
@Transactional(readOnly = true)
public class NewsPostServiceImpl implements NewsPostService {

	private final NewsPostRepository repository;

	public NewsPostServiceImpl(NewsPostRepository repository) {
		this.repository = repository;
	}

	@Override
	public List<NewsPostResponse> findAll() {
		return repository.findAllByOrderByCreatedAtDesc().stream().map(this::toResponse).toList();
	}

	@Override
	public List<NewsPostResponse> findPublished() {
		return repository.findAllByOrderByCreatedAtDesc().stream()
				.filter(e -> e.getStatus() == CmsPublishStatus.PUBLISHED)
				.map(this::toResponse)
				.toList();
	}

	@Override
	public NewsPostResponse findById(Long id) {
		return toResponse(findOrThrow(id));
	}

	@Override
	@Transactional
	public NewsPostResponse create(NewsPostRequest request) {
		NewsPost entity = new NewsPost();
		applyRequest(entity, request);
		return toResponse(repository.save(entity));
	}

	@Override
	@Transactional
	public NewsPostResponse update(Long id, NewsPostRequest request) {
		NewsPost entity = findOrThrow(id);
		applyRequest(entity, request);
		return toResponse(repository.save(entity));
	}

	@Override
	@Transactional
	public void delete(Long id) {
		if (!repository.existsById(id)) {
			throw new ResourceNotFoundException("NewsPost not found with id: " + id);
		}
		repository.deleteById(id);
	}

	private NewsPost findOrThrow(Long id) {
		return repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("NewsPost not found with id: " + id));
	}

	private void applyRequest(NewsPost entity, NewsPostRequest request) {
		entity.setSlug(request.getSlug());
		entity.setTitle(request.getTitle());
		entity.setSummary(request.getSummary());
		entity.setBody(request.getBody());
		entity.setImageUrl(request.getImageUrl());
		entity.setAuthor(request.getAuthor());
		entity.setStatus(request.getStatus());
		entity.setPublishedAt(request.getPublishedAt());
	}

	private NewsPostResponse toResponse(NewsPost entity) {
		NewsPostResponse r = new NewsPostResponse();
		r.setId(entity.getId());
		r.setSlug(entity.getSlug());
		r.setTitle(entity.getTitle());
		r.setSummary(entity.getSummary());
		r.setBody(entity.getBody());
		r.setImageUrl(entity.getImageUrl());
		r.setAuthor(entity.getAuthor());
		r.setStatus(entity.getStatus());
		r.setPublishedAt(entity.getPublishedAt());
		r.setCreatedAt(entity.getCreatedAt());
		r.setUpdatedAt(entity.getUpdatedAt());
		return r;
	}
}
