package com.bezkoder.spring.jpa.postgresql.service;

import org.springframework.stereotype.Service;

import com.bezkoder.spring.jpa.postgresql.entity.AuditLog;
import com.bezkoder.spring.jpa.postgresql.repository.AuditLogRepository;

/** Records authentication events (logins, signups, resets) to the audit_logs table. */
@Service
public class AuthAuditService {

	private final AuditLogRepository auditLogRepository;

	public AuthAuditService(AuditLogRepository auditLogRepository) {
		this.auditLogRepository = auditLogRepository;
	}

	public void record(String action, String actorEmail, String details) {
		try {
			AuditLog log = new AuditLog();
			log.setAction(truncate(action, 50));
			log.setEntityType("AUTH");
			log.setActorEmail(truncate(actorEmail, 200));
			log.setDetails(details);
			auditLogRepository.save(log);
		} catch (Exception ignored) {
			// Audit must never block an auth flow.
		}
	}

	private String truncate(String value, int max) {
		if (value == null) {
			return null;
		}
		return value.length() <= max ? value : value.substring(0, max);
	}
}
