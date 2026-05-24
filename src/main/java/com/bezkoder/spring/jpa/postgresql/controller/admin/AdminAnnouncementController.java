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

import com.bezkoder.spring.jpa.postgresql.dto.announcement.AnnouncementRequest;
import com.bezkoder.spring.jpa.postgresql.dto.announcement.AnnouncementResponse;
import com.bezkoder.spring.jpa.postgresql.service.AnnouncementService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/admin/announcements")
public class AdminAnnouncementController {

	private final AnnouncementService announcementService;

	public AdminAnnouncementController(AnnouncementService announcementService) {
		this.announcementService = announcementService;
	}

	@GetMapping
	public ResponseEntity<List<AnnouncementResponse>> getAll() {
		return ResponseEntity.ok(announcementService.getAllAnnouncements());
	}

	@GetMapping("/{id}")
	public ResponseEntity<AnnouncementResponse> getById(@PathVariable Long id) {
		return ResponseEntity.ok(announcementService.getAnnouncementById(id));
	}

	@PostMapping
	public ResponseEntity<AnnouncementResponse> create(@Valid @RequestBody AnnouncementRequest request) {
		AnnouncementResponse created = announcementService.createAnnouncement(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(created);
	}

	@PutMapping("/{id}")
	public ResponseEntity<AnnouncementResponse> update(
			@PathVariable Long id,
			@Valid @RequestBody AnnouncementRequest request) {
		return ResponseEntity.ok(announcementService.updateAnnouncement(id, request));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		announcementService.deleteAnnouncement(id);
		return ResponseEntity.noContent().build();
	}
}
