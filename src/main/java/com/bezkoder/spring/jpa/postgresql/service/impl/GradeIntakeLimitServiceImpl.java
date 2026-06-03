package com.bezkoder.spring.jpa.postgresql.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import com.bezkoder.spring.jpa.postgresql.service.GradeIntakeLimitService;
import org.springframework.transaction.annotation.Transactional;

import com.bezkoder.spring.jpa.postgresql.dto.intake.GradeIntakeLimitRequest;
import com.bezkoder.spring.jpa.postgresql.dto.intake.GradeIntakeLimitResponse;
import com.bezkoder.spring.jpa.postgresql.entity.GradeIntakeLimit;
import com.bezkoder.spring.jpa.postgresql.exception.ResourceNotFoundException;
import com.bezkoder.spring.jpa.postgresql.repository.GradeIntakeLimitRepository;

@Service
@Transactional(readOnly = true)
public class GradeIntakeLimitServiceImpl implements GradeIntakeLimitService {

	private final GradeIntakeLimitRepository repository;

	public GradeIntakeLimitServiceImpl(GradeIntakeLimitRepository repository) {
		this.repository = repository;
	}

	@Override
	public List<GradeIntakeLimitResponse> findAll() {
		return repository.findAllByOrderByCreatedAtDesc().stream().map(this::toResponse).toList();
	}

	@Override
	public List<GradeIntakeLimitResponse> findPublished() {
		return findAll();
	}

	@Override
	public GradeIntakeLimitResponse findById(Long id) {
		return toResponse(findOrThrow(id));
	}

	@Override
	@Transactional
	public GradeIntakeLimitResponse create(GradeIntakeLimitRequest request) {
		GradeIntakeLimit entity = new GradeIntakeLimit();
		applyRequest(entity, request);
		return toResponse(repository.save(entity));
	}

	@Override
	@Transactional
	public GradeIntakeLimitResponse update(Long id, GradeIntakeLimitRequest request) {
		GradeIntakeLimit entity = findOrThrow(id);
		applyRequest(entity, request);
		return toResponse(repository.save(entity));
	}

	@Override
	@Transactional
	public void delete(Long id) {
		if (!repository.existsById(id)) {
			throw new ResourceNotFoundException("GradeIntakeLimit not found with id: " + id);
		}
		repository.deleteById(id);
	}

	private GradeIntakeLimit findOrThrow(Long id) {
		return repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("GradeIntakeLimit not found with id: " + id));
	}

	private void applyRequest(GradeIntakeLimit entity, GradeIntakeLimitRequest request) {
		entity.setGradeKey(request.getGradeKey());
		entity.setAcademicYear(request.getAcademicYear());
		entity.setMaxApplications(request.getMaxApplications());
		entity.setWaitlistEnabled(request.getWaitlistEnabled());
	}

	private GradeIntakeLimitResponse toResponse(GradeIntakeLimit entity) {
		GradeIntakeLimitResponse r = new GradeIntakeLimitResponse();
		r.setId(entity.getId());
		r.setGradeKey(entity.getGradeKey());
		r.setAcademicYear(entity.getAcademicYear());
		r.setMaxApplications(entity.getMaxApplications());
		r.setWaitlistEnabled(entity.getWaitlistEnabled());
		r.setCreatedAt(entity.getCreatedAt());
		r.setUpdatedAt(entity.getUpdatedAt());
		return r;
	}
}
