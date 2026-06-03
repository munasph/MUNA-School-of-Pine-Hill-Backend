package com.bezkoder.spring.jpa.postgresql.service;

import java.util.List;

import com.bezkoder.spring.jpa.postgresql.dto.faq.FaqItemRequest;
import com.bezkoder.spring.jpa.postgresql.dto.faq.FaqItemResponse;

public interface FaqItemService {

	List<FaqItemResponse> findAll();

	List<FaqItemResponse> findPublished();

	FaqItemResponse findById(Long id);

	FaqItemResponse create(FaqItemRequest request);

	FaqItemResponse update(Long id, FaqItemRequest request);

	void delete(Long id);
}
