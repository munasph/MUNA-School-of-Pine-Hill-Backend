package com.bezkoder.spring.jpa.postgresql.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import com.bezkoder.spring.jpa.postgresql.service.FacultyMemberService;
import org.springframework.transaction.annotation.Transactional;

import com.bezkoder.spring.jpa.postgresql.dto.faculty.FacultyMemberRequest;
import com.bezkoder.spring.jpa.postgresql.dto.faculty.FacultyMemberResponse;
import com.bezkoder.spring.jpa.postgresql.entity.FacultyMember;
import com.bezkoder.spring.jpa.postgresql.exception.ResourceNotFoundException;
import com.bezkoder.spring.jpa.postgresql.repository.FacultyMemberRepository;
import com.bezkoder.spring.jpa.postgresql.entity.enums.CmsPublishStatus;

@Service
@Transactional(readOnly = true)
public class FacultyMemberServiceImpl implements FacultyMemberService {

	private final FacultyMemberRepository repository;

	public FacultyMemberServiceImpl(FacultyMemberRepository repository) {
		this.repository = repository;
	}

	@Override
	public List<FacultyMemberResponse> findAll() {
		return repository.findAllByOrderByCreatedAtDesc().stream().map(this::toResponse).toList();
	}

	@Override
	public List<FacultyMemberResponse> findPublished() {
		return repository.findAllByOrderByCreatedAtDesc().stream()
				.filter(e -> e.getStatus() == CmsPublishStatus.PUBLISHED)
				.map(this::toResponse)
				.toList();
	}

	@Override
	public FacultyMemberResponse findById(Long id) {
		return toResponse(findOrThrow(id));
	}

	@Override
	@Transactional
	public FacultyMemberResponse create(FacultyMemberRequest request) {
		FacultyMember entity = new FacultyMember();
		applyRequest(entity, request);
		return toResponse(repository.save(entity));
	}

	@Override
	@Transactional
	public FacultyMemberResponse update(Long id, FacultyMemberRequest request) {
		FacultyMember entity = findOrThrow(id);
		applyRequest(entity, request);
		return toResponse(repository.save(entity));
	}

	@Override
	@Transactional
	public void delete(Long id) {
		if (!repository.existsById(id)) {
			throw new ResourceNotFoundException("FacultyMember not found with id: " + id);
		}
		repository.deleteById(id);
	}

	private FacultyMember findOrThrow(Long id) {
		return repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("FacultyMember not found with id: " + id));
	}

	private void applyRequest(FacultyMember entity, FacultyMemberRequest request) {
		entity.setName(request.getName());
		entity.setRoleTitle(request.getRoleTitle());
		entity.setDepartment(request.getDepartment());
		entity.setEmail(request.getEmail());
		entity.setPhone(request.getPhone());
		entity.setBio(request.getBio());
		entity.setImageUrl(request.getImageUrl());
		entity.setSortOrder(request.getSortOrder());
		entity.setStatus(request.getStatus());
	}

	private FacultyMemberResponse toResponse(FacultyMember entity) {
		FacultyMemberResponse r = new FacultyMemberResponse();
		r.setId(entity.getId());
		r.setName(entity.getName());
		r.setRoleTitle(entity.getRoleTitle());
		r.setDepartment(entity.getDepartment());
		r.setEmail(entity.getEmail());
		r.setPhone(entity.getPhone());
		r.setBio(entity.getBio());
		r.setImageUrl(entity.getImageUrl());
		r.setSortOrder(entity.getSortOrder());
		r.setStatus(entity.getStatus());
		r.setCreatedAt(entity.getCreatedAt());
		r.setUpdatedAt(entity.getUpdatedAt());
		return r;
	}
}
