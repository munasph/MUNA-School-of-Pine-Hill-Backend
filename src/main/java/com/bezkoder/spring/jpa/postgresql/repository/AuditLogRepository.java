package com.bezkoder.spring.jpa.postgresql.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bezkoder.spring.jpa.postgresql.entity.AuditLog;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
	List<AuditLog> findTop100ByOrderByCreatedAtDesc();
}
