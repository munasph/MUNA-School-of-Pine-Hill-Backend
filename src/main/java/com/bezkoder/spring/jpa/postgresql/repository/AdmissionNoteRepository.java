package com.bezkoder.spring.jpa.postgresql.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bezkoder.spring.jpa.postgresql.entity.AdmissionNote;

public interface AdmissionNoteRepository extends JpaRepository<AdmissionNote, Long> {
	List<AdmissionNote> findAllByApplicationIdOrderByCreatedAtDesc(Long applicationId);
}
