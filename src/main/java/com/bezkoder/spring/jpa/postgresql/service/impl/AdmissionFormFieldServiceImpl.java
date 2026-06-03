package com.bezkoder.spring.jpa.postgresql.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import com.bezkoder.spring.jpa.postgresql.service.AdmissionFormFieldService;
import org.springframework.transaction.annotation.Transactional;

import com.bezkoder.spring.jpa.postgresql.dto.formfield.AdmissionFormFieldRequest;
import com.bezkoder.spring.jpa.postgresql.dto.formfield.AdmissionFormFieldResponse;
import com.bezkoder.spring.jpa.postgresql.entity.AdmissionFormField;
import com.bezkoder.spring.jpa.postgresql.exception.ResourceNotFoundException;
import com.bezkoder.spring.jpa.postgresql.repository.AdmissionFormFieldRepository;

@Service
@Transactional(readOnly = true)
public class AdmissionFormFieldServiceImpl implements AdmissionFormFieldService {

	private final AdmissionFormFieldRepository repository;

	public AdmissionFormFieldServiceImpl(AdmissionFormFieldRepository repository) {
		this.repository = repository;
	}

	@Override
	public List<AdmissionFormFieldResponse> findAll() {
		return repository.findAllByOrderBySortOrderAsc().stream().map(this::toResponse).toList();
	}

	@Override
	public List<AdmissionFormFieldResponse> findPublished() {
		return repository.findAllByOrderBySortOrderAsc().stream()
				.filter(e -> e.getActive())
				.map(this::toResponse)
				.toList();
	}

	@Override
	public AdmissionFormFieldResponse findById(Long id) {
		return toResponse(findOrThrow(id));
	}

	@Override
	@Transactional
	public AdmissionFormFieldResponse create(AdmissionFormFieldRequest request) {
		AdmissionFormField entity = new AdmissionFormField();
		applyRequest(entity, request);
		return toResponse(repository.save(entity));
	}

	@Override
	@Transactional
	public AdmissionFormFieldResponse update(Long id, AdmissionFormFieldRequest request) {
		AdmissionFormField entity = findOrThrow(id);
		applyRequest(entity, request);
		return toResponse(repository.save(entity));
	}

	@Override
	@Transactional
	public void delete(Long id) {
		if (!repository.existsById(id)) {
			throw new ResourceNotFoundException("AdmissionFormField not found with id: " + id);
		}
		repository.deleteById(id);
	}

	private AdmissionFormField findOrThrow(Long id) {
		return repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("AdmissionFormField not found with id: " + id));
	}

	private void applyRequest(AdmissionFormField entity, AdmissionFormFieldRequest request) {
		entity.setFieldKey(request.getFieldKey());
		entity.setLabel(request.getLabel());
		entity.setRequired(request.getRequired());
		entity.setActive(request.getActive());
		entity.setSortOrder(request.getSortOrder());
	}

	private AdmissionFormFieldResponse toResponse(AdmissionFormField entity) {
		AdmissionFormFieldResponse r = new AdmissionFormFieldResponse();
		r.setId(entity.getId());
		r.setFieldKey(entity.getFieldKey());
		r.setLabel(entity.getLabel());
		r.setRequired(entity.getRequired());
		r.setActive(entity.getActive());
		r.setSortOrder(entity.getSortOrder());
		return r;
	}
}
