package com.bezkoder.spring.jpa.postgresql.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import com.bezkoder.spring.jpa.postgresql.service.EmailCampaignService;
import org.springframework.transaction.annotation.Transactional;

import com.bezkoder.spring.jpa.postgresql.dto.campaign.EmailCampaignRequest;
import com.bezkoder.spring.jpa.postgresql.dto.campaign.EmailCampaignResponse;
import com.bezkoder.spring.jpa.postgresql.entity.EmailCampaign;
import com.bezkoder.spring.jpa.postgresql.exception.ResourceNotFoundException;
import com.bezkoder.spring.jpa.postgresql.repository.EmailCampaignRepository;
import com.bezkoder.spring.jpa.postgresql.entity.enums.CmsPublishStatus;

@Service
@Transactional(readOnly = true)
public class EmailCampaignServiceImpl implements EmailCampaignService {

	private final EmailCampaignRepository repository;

	public EmailCampaignServiceImpl(EmailCampaignRepository repository) {
		this.repository = repository;
	}

	@Override
	public List<EmailCampaignResponse> findAll() {
		return repository.findAllByOrderByCreatedAtDesc().stream().map(this::toResponse).toList();
	}

	@Override
	public List<EmailCampaignResponse> findPublished() {
		return repository.findAllByOrderByCreatedAtDesc().stream()
				.filter(e -> e.getStatus() == CmsPublishStatus.PUBLISHED)
				.map(this::toResponse)
				.toList();
	}

	@Override
	public EmailCampaignResponse findById(Long id) {
		return toResponse(findOrThrow(id));
	}

	@Override
	@Transactional
	public EmailCampaignResponse create(EmailCampaignRequest request) {
		EmailCampaign entity = new EmailCampaign();
		applyRequest(entity, request);
		return toResponse(repository.save(entity));
	}

	@Override
	@Transactional
	public EmailCampaignResponse update(Long id, EmailCampaignRequest request) {
		EmailCampaign entity = findOrThrow(id);
		applyRequest(entity, request);
		return toResponse(repository.save(entity));
	}

	@Override
	@Transactional
	public void delete(Long id) {
		if (!repository.existsById(id)) {
			throw new ResourceNotFoundException("EmailCampaign not found with id: " + id);
		}
		repository.deleteById(id);
	}

	private EmailCampaign findOrThrow(Long id) {
		return repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("EmailCampaign not found with id: " + id));
	}

	private void applyRequest(EmailCampaign entity, EmailCampaignRequest request) {
		entity.setSubject(request.getSubject());
		entity.setBody(request.getBody());
		entity.setStatus(request.getStatus());
		entity.setSentAt(request.getSentAt());
	}

	private EmailCampaignResponse toResponse(EmailCampaign entity) {
		EmailCampaignResponse r = new EmailCampaignResponse();
		r.setId(entity.getId());
		r.setSubject(entity.getSubject());
		r.setBody(entity.getBody());
		r.setStatus(entity.getStatus());
		r.setSentAt(entity.getSentAt());
		r.setCreatedAt(entity.getCreatedAt());
		r.setUpdatedAt(entity.getUpdatedAt());
		return r;
	}
}
