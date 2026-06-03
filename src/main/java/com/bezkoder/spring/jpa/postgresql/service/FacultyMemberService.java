package com.bezkoder.spring.jpa.postgresql.service;

import java.util.List;

import com.bezkoder.spring.jpa.postgresql.dto.faculty.FacultyMemberRequest;
import com.bezkoder.spring.jpa.postgresql.dto.faculty.FacultyMemberResponse;

public interface FacultyMemberService {

	List<FacultyMemberResponse> findAll();

	List<FacultyMemberResponse> findPublished();

	FacultyMemberResponse findById(Long id);

	FacultyMemberResponse create(FacultyMemberRequest request);

	FacultyMemberResponse update(Long id, FacultyMemberRequest request);

	void delete(Long id);
}
