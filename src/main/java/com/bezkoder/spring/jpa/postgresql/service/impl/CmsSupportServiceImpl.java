package com.bezkoder.spring.jpa.postgresql.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
import com.bezkoder.spring.jpa.postgresql.entity.AdminUser;
import com.bezkoder.spring.jpa.postgresql.entity.AdmissionDocument;
import com.bezkoder.spring.jpa.postgresql.entity.AdmissionNote;
import com.bezkoder.spring.jpa.postgresql.entity.AnalyticsSettings;
import com.bezkoder.spring.jpa.postgresql.entity.AuditLog;
import com.bezkoder.spring.jpa.postgresql.entity.NotificationSettings;
import com.bezkoder.spring.jpa.postgresql.exception.BadRequestException;
import com.bezkoder.spring.jpa.postgresql.exception.ResourceNotFoundException;
import com.bezkoder.spring.jpa.postgresql.repository.AdminUserRepository;
import com.bezkoder.spring.jpa.postgresql.repository.AdmissionApplicationRepository;
import com.bezkoder.spring.jpa.postgresql.repository.AdmissionDocumentRepository;
import com.bezkoder.spring.jpa.postgresql.repository.AdmissionNoteRepository;
import com.bezkoder.spring.jpa.postgresql.repository.AnalyticsSettingsRepository;
import com.bezkoder.spring.jpa.postgresql.repository.AuditLogRepository;
import com.bezkoder.spring.jpa.postgresql.repository.NotificationSettingsRepository;
import com.bezkoder.spring.jpa.postgresql.service.CmsSupportService;

@Service
@Transactional(readOnly = true)
public class CmsSupportServiceImpl implements CmsSupportService {

	private final AdminUserRepository adminUserRepository;
	private final AdmissionNoteRepository noteRepository;
	private final AdmissionDocumentRepository documentRepository;
	private final AdmissionApplicationRepository applicationRepository;
	private final AuditLogRepository auditLogRepository;
	private final NotificationSettingsRepository notificationSettingsRepository;
	private final AnalyticsSettingsRepository analyticsSettingsRepository;

	public CmsSupportServiceImpl(
			AdminUserRepository adminUserRepository,
			AdmissionNoteRepository noteRepository,
			AdmissionDocumentRepository documentRepository,
			AdmissionApplicationRepository applicationRepository,
			AuditLogRepository auditLogRepository,
			NotificationSettingsRepository notificationSettingsRepository,
			AnalyticsSettingsRepository analyticsSettingsRepository) {
		this.adminUserRepository = adminUserRepository;
		this.noteRepository = noteRepository;
		this.documentRepository = documentRepository;
		this.applicationRepository = applicationRepository;
		this.auditLogRepository = auditLogRepository;
		this.notificationSettingsRepository = notificationSettingsRepository;
		this.analyticsSettingsRepository = analyticsSettingsRepository;
	}

	@Override
	public List<AdminUserResponse> listUsers() {
		return adminUserRepository.findAllByOrderByEmailAsc().stream().map(this::toUserResponse).toList();
	}

	@Override
	@Transactional
	public AdminUserResponse createUser(AdminUserRequest request) {
		if (request.getPassword() == null || request.getPassword().isBlank()) {
			throw new BadRequestException("Password is required when creating a user.");
		}
		AdminUser entity = new AdminUser();
		entity.setEmail(request.getEmail());
		entity.setPasswordHash(hashPlaceholder(request.getPassword()));
		entity.setDisplayName(request.getDisplayName());
		entity.setRole(request.getRole());
		entity.setActive(request.isActive());
		return toUserResponse(adminUserRepository.save(entity));
	}

	@Override
	@Transactional
	public AdminUserResponse updateUser(Long id, AdminUserRequest request) {
		AdminUser entity = findUserOrThrow(id);
		entity.setEmail(request.getEmail());
		if (request.getPassword() != null && !request.getPassword().isBlank()) {
			entity.setPasswordHash(hashPlaceholder(request.getPassword()));
		}
		entity.setDisplayName(request.getDisplayName());
		entity.setRole(request.getRole());
		entity.setActive(request.isActive());
		return toUserResponse(adminUserRepository.save(entity));
	}

	@Override
	@Transactional
	public void deleteUser(Long id) {
		if (!adminUserRepository.existsById(id)) {
			throw new ResourceNotFoundException("User not found with id: " + id);
		}
		adminUserRepository.deleteById(id);
	}

	@Override
	public List<AdmissionNoteResponse> listNotes(Long applicationId) {
		ensureApplicationExists(applicationId);
		return noteRepository.findAllByApplicationIdOrderByCreatedAtDesc(applicationId)
				.stream().map(this::toNoteResponse).toList();
	}

	@Override
	@Transactional
	public AdmissionNoteResponse createNote(Long applicationId, AdmissionNoteRequest request) {
		ensureApplicationExists(applicationId);
		AdmissionNote entity = new AdmissionNote();
		entity.setApplicationId(applicationId);
		entity.setBody(request.getBody());
		entity.setAuthorEmail(request.getAuthorEmail());
		return toNoteResponse(noteRepository.save(entity));
	}

	@Override
	@Transactional
	public void deleteNote(Long noteId) {
		if (!noteRepository.existsById(noteId)) {
			throw new ResourceNotFoundException("Note not found with id: " + noteId);
		}
		noteRepository.deleteById(noteId);
	}

	@Override
	public List<AdmissionDocumentResponse> listDocuments(Long applicationId) {
		ensureApplicationExists(applicationId);
		return documentRepository.findAllByApplicationIdOrderByUploadedAtDesc(applicationId)
				.stream().map(this::toDocumentResponse).toList();
	}

	@Override
	@Transactional
	public AdmissionDocumentResponse createDocument(Long applicationId, AdmissionDocumentRequest request) {
		ensureApplicationExists(applicationId);
		AdmissionDocument entity = new AdmissionDocument();
		entity.setApplicationId(applicationId);
		entity.setDocType(request.getDocType());
		entity.setFileName(request.getFileName());
		entity.setFileUrl(request.getFileUrl());
		return toDocumentResponse(documentRepository.save(entity));
	}

	@Override
	@Transactional
	public void deleteDocument(Long documentId) {
		if (!documentRepository.existsById(documentId)) {
			throw new ResourceNotFoundException("Document not found with id: " + documentId);
		}
		documentRepository.deleteById(documentId);
	}

	@Override
	public List<AuditLogResponse> listAuditLogs() {
		return auditLogRepository.findTop100ByOrderByCreatedAtDesc()
				.stream().map(this::toAuditResponse).toList();
	}

	@Override
	public NotificationSettingsResponse getNotificationSettings() {
		return toNotificationResponse(getOrCreateNotifications());
	}

	@Override
	@Transactional
	public NotificationSettingsResponse updateNotificationSettings(NotificationSettingsRequest request) {
		NotificationSettings entity = getOrCreateNotifications();
		entity.setEmailOnNewAdmission(request.isEmailOnNewAdmission());
		entity.setEmailOnNewContact(request.isEmailOnNewContact());
		entity.setAdminNotificationEmail(request.getAdminNotificationEmail());
		return toNotificationResponse(notificationSettingsRepository.save(entity));
	}

	@Override
	public AnalyticsSettingsResponse getAnalyticsSettings() {
		return toAnalyticsResponse(getOrCreateAnalytics());
	}

	@Override
	@Transactional
	public AnalyticsSettingsResponse updateAnalyticsSettings(AnalyticsSettingsRequest request) {
		AnalyticsSettings entity = getOrCreateAnalytics();
		entity.setEnabled(request.isEnabled());
		entity.setGaMeasurementId(request.getGaMeasurementId());
		return toAnalyticsResponse(analyticsSettingsRepository.save(entity));
	}

	private void ensureApplicationExists(Long applicationId) {
		if (!applicationRepository.existsById(applicationId)) {
			throw new ResourceNotFoundException("Application not found with id: " + applicationId);
		}
	}

	private AdminUser findUserOrThrow(Long id) {
		return adminUserRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
	}

	private NotificationSettings getOrCreateNotifications() {
		return notificationSettingsRepository.findById(NotificationSettings.SINGLETON_ID)
				.orElseGet(() -> notificationSettingsRepository.save(new NotificationSettings()));
	}

	private AnalyticsSettings getOrCreateAnalytics() {
		return analyticsSettingsRepository.findById(AnalyticsSettings.SINGLETON_ID)
				.orElseGet(() -> analyticsSettingsRepository.save(new AnalyticsSettings()));
	}

	private String hashPlaceholder(String password) {
		return "SCAFFOLD:" + password;
	}

	private AdminUserResponse toUserResponse(AdminUser entity) {
		AdminUserResponse r = new AdminUserResponse();
		r.setId(entity.getId());
		r.setEmail(entity.getEmail());
		r.setDisplayName(entity.getDisplayName());
		r.setRole(entity.getRole());
		r.setActive(entity.isActive());
		r.setLastLoginAt(entity.getLastLoginAt());
		r.setCreatedAt(entity.getCreatedAt());
		return r;
	}

	private AdmissionNoteResponse toNoteResponse(AdmissionNote entity) {
		AdmissionNoteResponse r = new AdmissionNoteResponse();
		r.setId(entity.getId());
		r.setApplicationId(entity.getApplicationId());
		r.setBody(entity.getBody());
		r.setAuthorEmail(entity.getAuthorEmail());
		r.setCreatedAt(entity.getCreatedAt());
		return r;
	}

	private AdmissionDocumentResponse toDocumentResponse(AdmissionDocument entity) {
		AdmissionDocumentResponse r = new AdmissionDocumentResponse();
		r.setId(entity.getId());
		r.setApplicationId(entity.getApplicationId());
		r.setDocType(entity.getDocType());
		r.setFileName(entity.getFileName());
		r.setFileUrl(entity.getFileUrl());
		r.setUploadedAt(entity.getUploadedAt());
		return r;
	}

	private AuditLogResponse toAuditResponse(AuditLog entity) {
		AuditLogResponse r = new AuditLogResponse();
		r.setId(entity.getId());
		r.setAction(entity.getAction());
		r.setEntityType(entity.getEntityType());
		r.setEntityId(entity.getEntityId());
		r.setActorEmail(entity.getActorEmail());
		r.setDetails(entity.getDetails());
		r.setCreatedAt(entity.getCreatedAt());
		return r;
	}

	private NotificationSettingsResponse toNotificationResponse(NotificationSettings entity) {
		NotificationSettingsResponse r = new NotificationSettingsResponse();
		r.setEmailOnNewAdmission(entity.isEmailOnNewAdmission());
		r.setEmailOnNewContact(entity.isEmailOnNewContact());
		r.setAdminNotificationEmail(entity.getAdminNotificationEmail());
		r.setUpdatedAt(entity.getUpdatedAt());
		return r;
	}

	private AnalyticsSettingsResponse toAnalyticsResponse(AnalyticsSettings entity) {
		AnalyticsSettingsResponse r = new AnalyticsSettingsResponse();
		r.setEnabled(entity.isEnabled());
		r.setGaMeasurementId(entity.getGaMeasurementId());
		r.setUpdatedAt(entity.getUpdatedAt());
		return r;
	}
}
