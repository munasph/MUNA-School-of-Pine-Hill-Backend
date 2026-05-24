package com.bezkoder.spring.jpa.postgresql.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bezkoder.spring.jpa.postgresql.dto.admission.AdmissionSubmitResponse;
import com.bezkoder.spring.jpa.postgresql.dto.admission.CreateAdmissionRequest;
import com.bezkoder.spring.jpa.postgresql.service.AdmissionService;

import jakarta.validation.Valid;

/** Public admission form endpoint — matches Angular {@code POST /api/admission}. */
@RestController
@RequestMapping("/api/admission")
public class AdmissionController {

	private final AdmissionService admissionService;

	public AdmissionController(AdmissionService admissionService) {
		this.admissionService = admissionService;
	}

	@PostMapping
	public ResponseEntity<AdmissionSubmitResponse> submitApplication(
			@Valid @RequestBody CreateAdmissionRequest request) {
		AdmissionSubmitResponse response = admissionService.submitApplication(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
}
