package com.bezkoder.spring.jpa.postgresql.service;

import java.util.List;

import com.bezkoder.spring.jpa.postgresql.dto.intake.GradeIntakeLimitRequest;
import com.bezkoder.spring.jpa.postgresql.dto.intake.GradeIntakeLimitResponse;

public interface GradeIntakeLimitService {

	List<GradeIntakeLimitResponse> findAll();

	List<GradeIntakeLimitResponse> findPublished();

	GradeIntakeLimitResponse findById(Long id);

	GradeIntakeLimitResponse create(GradeIntakeLimitRequest request);

	GradeIntakeLimitResponse update(Long id, GradeIntakeLimitRequest request);

	void delete(Long id);
}
