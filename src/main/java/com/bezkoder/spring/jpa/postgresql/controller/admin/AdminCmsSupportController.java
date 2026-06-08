package com.bezkoder.spring.jpa.postgresql.controller.admin;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bezkoder.spring.jpa.postgresql.dto.cms.AdminUserRequest;
import com.bezkoder.spring.jpa.postgresql.dto.cms.AdminUserResponse;
import com.bezkoder.spring.jpa.postgresql.dto.cms.AdmissionDocumentRequest;
import com.bezkoder.spring.jpa.postgresql.dto.cms.AdmissionDocumentResponse;
import com.bezkoder.spring.jpa.postgresql.dto.cms.AdmissionNoteRequest;
import com.bezkoder.spring.jpa.postgresql.dto.cms.AdmissionNoteResponse;
import com.bezkoder.spring.jpa.postgresql.dto.cms.AnalyticsSettingsRequest;
import com.bezkoder.spring.jpa.postgresql.dto.cms.AnalyticsSettingsResponse;
import com.bezkoder.spring.jpa.postgresql.dto.cms.AuditLogResponse;
import com.bezkoder.spring.jpa.postgresql.dto.cms.NotificationSettingsRequest;
import com.bezkoder.spring.jpa.postgresql.dto.cms.NotificationSettingsResponse;
import com.bezkoder.spring.jpa.postgresql.service.CmsSupportService;

import jakarta.validation.Valid;

@RestController
public class AdminCmsSupportController {

	private final CmsSupportService cmsSupportService;

	public AdminCmsSupportController(CmsSupportService cmsSupportService) {
		this.cmsSupportService = cmsSupportService;
	}

	@GetMapping("/api/admin/users")
	public ResponseEntity<List<AdminUserResponse>> listUsers() {
		return ResponseEntity.ok(cmsSupportService.listUsers());
	}

	@PostMapping("/api/admin/users")
	public ResponseEntity<AdminUserResponse> createUser(@Valid @RequestBody AdminUserRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED).body(cmsSupportService.createUser(request));
	}

	@PutMapping("/api/admin/users/{id}")
	public ResponseEntity<AdminUserResponse> updateUser(
			@PathVariable Long id, @Valid @RequestBody AdminUserRequest request) {
		return ResponseEntity.ok(cmsSupportService.updateUser(id, request));
	}

	@DeleteMapping("/api/admin/users/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
		cmsSupportService.deleteUser(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/api/admin/admissions/{applicationId}/notes")
	public ResponseEntity<List<AdmissionNoteResponse>> listNotes(@PathVariable Long applicationId) {
		return ResponseEntity.ok(cmsSupportService.listNotes(applicationId));
	}

	@PostMapping("/api/admin/admissions/{applicationId}/notes")
	public ResponseEntity<AdmissionNoteResponse> createNote(
			@PathVariable Long applicationId, @Valid @RequestBody AdmissionNoteRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(cmsSupportService.createNote(applicationId, request));
	}

	@DeleteMapping("/api/admin/admission-notes/{noteId}")
	public ResponseEntity<Void> deleteNote(@PathVariable Long noteId) {
		cmsSupportService.deleteNote(noteId);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/api/admin/admissions/{applicationId}/documents")
	public ResponseEntity<List<AdmissionDocumentResponse>> listDocuments(@PathVariable Long applicationId) {
		return ResponseEntity.ok(cmsSupportService.listDocuments(applicationId));
	}

	@PostMapping("/api/admin/admissions/{applicationId}/documents")
	public ResponseEntity<AdmissionDocumentResponse> createDocument(
			@PathVariable Long applicationId, @Valid @RequestBody AdmissionDocumentRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(cmsSupportService.createDocument(applicationId, request));
	}

	@DeleteMapping("/api/admin/admission-documents/{documentId}")
	public ResponseEntity<Void> deleteDocument(@PathVariable Long documentId) {
		cmsSupportService.deleteDocument(documentId);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/api/admin/admission-documents/{documentId}/download")
	public ResponseEntity<Resource> downloadDocument(@PathVariable Long documentId) {
		AdmissionDocumentResponse document = cmsSupportService.getDocument(documentId);
		Resource resource = cmsSupportService.loadDocumentFile(documentId);
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION,
						"attachment; filename=\"" + sanitizeFilename(document.getFileName()) + "\"")
				.contentType(MediaType.APPLICATION_OCTET_STREAM)
				.body(resource);
	}

	private static String sanitizeFilename(String filename) {
		if (filename == null || filename.isBlank()) {
			return "document";
		}
		return filename.replace("\"", "'");
	}

	@GetMapping("/api/admin/audit-logs")
	public ResponseEntity<List<AuditLogResponse>> listAuditLogs() {
		return ResponseEntity.ok(cmsSupportService.listAuditLogs());
	}

	@GetMapping("/api/admin/notifications")
	public ResponseEntity<NotificationSettingsResponse> getNotifications() {
		return ResponseEntity.ok(cmsSupportService.getNotificationSettings());
	}

	@PutMapping("/api/admin/notifications")
	public ResponseEntity<NotificationSettingsResponse> updateNotifications(
			@Valid @RequestBody NotificationSettingsRequest request) {
		return ResponseEntity.ok(cmsSupportService.updateNotificationSettings(request));
	}

	@GetMapping("/api/admin/analytics")
	public ResponseEntity<AnalyticsSettingsResponse> getAnalytics() {
		return ResponseEntity.ok(cmsSupportService.getAnalyticsSettings());
	}

	@PutMapping("/api/admin/analytics")
	public ResponseEntity<AnalyticsSettingsResponse> updateAnalytics(
			@Valid @RequestBody AnalyticsSettingsRequest request) {
		return ResponseEntity.ok(cmsSupportService.updateAnalyticsSettings(request));
	}
}
