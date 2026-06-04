package com.bezkoder.spring.jpa.postgresql.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bezkoder.spring.jpa.postgresql.entity.AdmissionApplication;
import com.bezkoder.spring.jpa.postgresql.entity.enums.ApplicationStatus;

@Repository
public interface AdmissionApplicationRepository extends JpaRepository<AdmissionApplication, Long> {

	List<AdmissionApplication> findAllByOrderBySubmittedAtDesc();

	long countByStatus(ApplicationStatus status);

	Optional<AdmissionApplication> findByApplicationIdIgnoreCase(String applicationId);
}
