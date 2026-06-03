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

import com.bezkoder.spring.jpa.postgresql.dto.gallery.GalleryItemRequest;
import com.bezkoder.spring.jpa.postgresql.dto.gallery.GalleryItemResponse;
import com.bezkoder.spring.jpa.postgresql.service.GalleryItemService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/admin/gallery")
public class AdminGalleryItemController {

	private final GalleryItemService service;

	public AdminGalleryItemController(GalleryItemService service) {
		this.service = service;
	}

	@GetMapping
	public ResponseEntity<List<GalleryItemResponse>> getAll() {
		return ResponseEntity.ok(service.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<GalleryItemResponse> getById(@PathVariable Long id) {
		return ResponseEntity.ok(service.findById(id));
	}

	@PostMapping
	public ResponseEntity<GalleryItemResponse> create(@Valid @RequestBody GalleryItemRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
	}

	@PutMapping("/{id}")
	public ResponseEntity<GalleryItemResponse> update(
			@PathVariable Long id,
			@Valid @RequestBody GalleryItemRequest request) {
		return ResponseEntity.ok(service.update(id, request));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}
