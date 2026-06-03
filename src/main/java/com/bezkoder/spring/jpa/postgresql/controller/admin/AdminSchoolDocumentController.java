package com.bezkoder.spring.jpa.postgresql.controller.admin;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bezkoder.spring.jpa.postgresql.dto.document.DocumentRequest;
import com.bezkoder.spring.jpa.postgresql.dto.document.DocumentResponse;
import com.bezkoder.spring.jpa.postgresql.service.SchoolDocumentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/admin/documents")
public class AdminSchoolDocumentController {

	private final SchoolDocumentService service;

	public AdminSchoolDocumentController(SchoolDocumentService service) {
		this.service = service;
	}

	@GetMapping
	public ResponseEntity<List<DocumentResponse>> getAll() {
		return ResponseEntity.ok(service.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<DocumentResponse> getById(@PathVariable Long id) {
		return ResponseEntity.ok(service.findById(id));
	}

	@PostMapping
	public ResponseEntity<DocumentResponse> create(@Valid @RequestBody DocumentRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
	}

	@PutMapping("/{id}")
	public ResponseEntity<DocumentResponse> update(
			@PathVariable Long id,
			@Valid @RequestBody DocumentRequest request) {
		return ResponseEntity.ok(service.update(id, request));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}
