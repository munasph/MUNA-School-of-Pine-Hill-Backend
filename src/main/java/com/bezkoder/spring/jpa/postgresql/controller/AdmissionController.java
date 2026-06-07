package com.bezkoder.spring.jpa.postgresql.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bezkoder.spring.jpa.postgresql.dto.admission.AdmissionSubmitResponse;
import com.bezkoder.spring.jpa.postgresql.dto.admission.CreateAdmissionRequest;
import com.bezkoder.spring.jpa.postgresql.service.AdmissionService;

import jakarta.validation.Valid;

import java.util.List;

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

	@PostMapping(value = "/with-documents", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<AdmissionSubmitResponse> submitApplicationWithDocuments(
			@Valid @RequestPart("application") CreateAdmissionRequest request,
			@RequestPart("files") List<MultipartFile> files,
			@RequestParam("docTypes") List<String> docTypes) {
		AdmissionSubmitResponse response = admissionService.submitApplicationWithDocuments(request, files, docTypes);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
}
