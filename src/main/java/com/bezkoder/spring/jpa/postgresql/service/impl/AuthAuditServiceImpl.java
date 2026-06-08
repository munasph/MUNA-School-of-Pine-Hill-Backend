package com.bezkoder.spring.jpa.postgresql.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bezkoder.spring.jpa.postgresql.entity.AuditLog;
import com.bezkoder.spring.jpa.postgresql.repository.AuditLogRepository;
import com.bezkoder.spring.jpa.postgresql.service.AuthAuditService;

@Service
public class AuthAuditServiceImpl implements AuthAuditService {

	private final AuditLogRepository auditLogRepository;

	public AuthAuditServiceImpl(AuditLogRepository auditLogRepository) {
		this.auditLogRepository = auditLogRepository;
	}

	@Override
	@Transactional
	public void log(String action, String entityType, String entityId, String actorEmail, String details) {
		AuditLog entry = new AuditLog();
		entry.setAction(action);
		entry.setEntityType(entityType);
		entry.setEntityId(entityId);
		entry.setActorEmail(actorEmail);
		entry.setDetails(details);
		auditLogRepository.save(entry);
	}
}
