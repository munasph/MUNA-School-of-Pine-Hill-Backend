package com.bezkoder.spring.jpa.postgresql.service;

import java.util.List;

import com.bezkoder.spring.jpa.postgresql.dto.document.DocumentRequest;
import com.bezkoder.spring.jpa.postgresql.dto.document.DocumentResponse;

public interface SchoolDocumentService {

	List<DocumentResponse> findAll();

	List<DocumentResponse> findPublished();

	DocumentResponse findById(Long id);

	DocumentResponse create(DocumentRequest request);

	DocumentResponse update(Long id, DocumentRequest request);

	void delete(Long id);
}
