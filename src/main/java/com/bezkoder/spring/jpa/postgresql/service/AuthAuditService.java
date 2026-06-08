package com.bezkoder.spring.jpa.postgresql.service;

public interface AuthAuditService {

	void log(String action, String entityType, String entityId, String actorEmail, String details);
}
