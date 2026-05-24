package com.bezkoder.spring.jpa.postgresql.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bezkoder.spring.jpa.postgresql.dto.admission.AdmissionResponse;
import com.bezkoder.spring.jpa.postgresql.dto.admission.AdmissionSubmitResponse;
import com.bezkoder.spring.jpa.postgresql.dto.admission.CreateAdmissionRequest;
import com.bezkoder.spring.jpa.postgresql.dto.admission.UpdateAdmissionStatusRequest;
import com.bezkoder.spring.jpa.postgresql.exception.ResourceNotFoundException;
import com.bezkoder.spring.jpa.postgresql.mapper.EntityMapper;
import com.bezkoder.spring.jpa.postgresql.model.AdmissionApplication;
import com.bezkoder.spring.jpa.postgresql.model.ApplicationStatus;
import com.bezkoder.spring.jpa.postgresql.repository.AdmissionApplicationRepository;
import com.bezkoder.spring.jpa.postgresql.service.AdmissionService;

@Service
@Transactional(readOnly = true)
public class AdmissionServiceImpl implements AdmissionService {

	private final AdmissionApplicationRepository admissionRepository;

	public AdmissionServiceImpl(AdmissionApplicationRepository admissionRepository) {
		this.admissionRepository = admissionRepository;
	}

	@Override
	@Transactional
	public AdmissionSubmitResponse submitApplication(CreateAdmissionRequest request) {
		AdmissionApplication entity = new AdmissionApplication();
		entity.setApplicationId("APP-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
		entity.setFullName(request.getFullName());
		entity.setDob(request.getDob());
		entity.setClassApplying(request.getClassApplying());
		entity.setGender(request.getGender());
		entity.setParentName(request.getParentName());
		entity.setParentPhone(request.getParentPhone());
		entity.setStatus(ApplicationStatus.PENDING);

		AdmissionApplication saved = admissionRepository.save(entity);

		return new AdmissionSubmitResponse(
				true,
				saved.getApplicationId(),
				"Application received.");
	}

	@Override
	public List<AdmissionResponse> getAllApplications() {
		return admissionRepository.findAllByOrderBySubmittedAtDesc().stream()
				.map(EntityMapper::toAdmissionResponse)
				.toList();
	}

	@Override
	public List<AdmissionResponse> getApplicationsByStatus(ApplicationStatus status) {
		return admissionRepository.findAllByOrderBySubmittedAtDesc().stream()
				.filter(app -> app.getStatus() == status)
				.map(EntityMapper::toAdmissionResponse)
				.toList();
	}

	@Override
	public AdmissionResponse getApplicationById(Long id) {
		AdmissionApplication entity = admissionRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Application not found with id: " + id));
		return EntityMapper.toAdmissionResponse(entity);
	}

	@Override
	@Transactional
	public AdmissionResponse updateApplicationStatus(Long id, UpdateAdmissionStatusRequest request) {
		AdmissionApplication entity = admissionRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Application not found with id: " + id));
		entity.setStatus(request.getStatus());
		return EntityMapper.toAdmissionResponse(admissionRepository.save(entity));
	}

	@Override
	@Transactional
	public void deleteApplication(Long id) {
		if (!admissionRepository.existsById(id)) {
			throw new ResourceNotFoundException("Application not found with id: " + id);
		}
		admissionRepository.deleteById(id);
	}
}
