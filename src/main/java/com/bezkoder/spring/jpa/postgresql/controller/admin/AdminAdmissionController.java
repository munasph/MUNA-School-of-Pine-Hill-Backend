package com.bezkoder.spring.jpa.postgresql.controller.admin;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bezkoder.spring.jpa.postgresql.dto.admission.AdmissionResponse;
import com.bezkoder.spring.jpa.postgresql.dto.admission.UpdateAdmissionStatusRequest;
import com.bezkoder.spring.jpa.postgresql.entity.enums.ApplicationStatus;
import com.bezkoder.spring.jpa.postgresql.service.AdmissionService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/admin/admissions")
public class AdminAdmissionController {

	private final AdmissionService admissionService;

	public AdminAdmissionController(AdmissionService admissionService) {
		this.admissionService = admissionService;
	}

	@GetMapping
	public ResponseEntity<List<AdmissionResponse>> getAllApplications(
			@RequestParam(required = false) ApplicationStatus status) {
		List<AdmissionResponse> applications = status == null
				? admissionService.getAllApplications()
				: admissionService.getApplicationsByStatus(status);
		return ResponseEntity.ok(applications);
	}

	@GetMapping("/{id}")
	public ResponseEntity<AdmissionResponse> getApplicationById(@PathVariable Long id) {
		return ResponseEntity.ok(admissionService.getApplicationById(id));
	}

	@PatchMapping("/{id}/status")
	public ResponseEntity<AdmissionResponse> updateStatus(
			@PathVariable Long id,
			@Valid @RequestBody UpdateAdmissionStatusRequest request) {
		return ResponseEntity.ok(admissionService.updateApplicationStatus(id, request));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteApplication(@PathVariable Long id) {
		admissionService.deleteApplication(id);
		return ResponseEntity.noContent().build();
	}
}
