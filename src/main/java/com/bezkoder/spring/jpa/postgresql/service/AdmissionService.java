package com.bezkoder.spring.jpa.postgresql.service;

import java.util.List;

import com.bezkoder.spring.jpa.postgresql.dto.admission.AdmissionResponse;
import com.bezkoder.spring.jpa.postgresql.dto.admission.AdmissionSubmitResponse;
import com.bezkoder.spring.jpa.postgresql.dto.admission.CreateAdmissionRequest;
import com.bezkoder.spring.jpa.postgresql.dto.admission.UpdateAdmissionStatusRequest;
import com.bezkoder.spring.jpa.postgresql.entity.enums.ApplicationStatus;

public interface AdmissionService {

	AdmissionSubmitResponse submitApplication(CreateAdmissionRequest request);

	List<AdmissionResponse> getAllApplications();

	List<AdmissionResponse> getApplicationsByStatus(ApplicationStatus status);

	AdmissionResponse getApplicationById(Long id);

	AdmissionResponse updateApplicationStatus(Long id, UpdateAdmissionStatusRequest request);

	void deleteApplication(Long id);

	String exportApplicationsAsCsv(ApplicationStatus status);
}
