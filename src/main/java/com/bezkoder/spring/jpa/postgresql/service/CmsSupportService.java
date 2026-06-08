package com.bezkoder.spring.jpa.postgresql.service;

import java.util.List;

import org.springframework.core.io.Resource;

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

public interface CmsSupportService {

	List<AdminUserResponse> listUsers();
	AdminUserResponse createUser(AdminUserRequest request);
	AdminUserResponse updateUser(Long id, AdminUserRequest request);
	void deleteUser(Long id);

	List<AdmissionNoteResponse> listNotes(Long applicationId);
	AdmissionNoteResponse createNote(Long applicationId, AdmissionNoteRequest request);
	void deleteNote(Long noteId);

	List<AdmissionDocumentResponse> listDocuments(Long applicationId);
	AdmissionDocumentResponse createDocument(Long applicationId, AdmissionDocumentRequest request);
	void deleteDocument(Long documentId);
	AdmissionDocumentResponse getDocument(Long documentId);
	Resource loadDocumentFile(Long documentId);

	List<AuditLogResponse> listAuditLogs();

	NotificationSettingsResponse getNotificationSettings();
	NotificationSettingsResponse updateNotificationSettings(NotificationSettingsRequest request);

	AnalyticsSettingsResponse getAnalyticsSettings();
	AnalyticsSettingsResponse updateAnalyticsSettings(AnalyticsSettingsRequest request);
}
