package com.bezkoder.spring.jpa.postgresql.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bezkoder.spring.jpa.postgresql.entity.AdmissionDocument;

public interface AdmissionDocumentRepository extends JpaRepository<AdmissionDocument, Long> {
	List<AdmissionDocument> findAllByApplicationIdOrderByUploadedAtDesc(Long applicationId);
}
