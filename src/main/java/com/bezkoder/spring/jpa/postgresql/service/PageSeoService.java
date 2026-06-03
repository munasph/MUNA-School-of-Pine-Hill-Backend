package com.bezkoder.spring.jpa.postgresql.service;

import java.util.List;

import com.bezkoder.spring.jpa.postgresql.dto.seo.PageSeoRequest;
import com.bezkoder.spring.jpa.postgresql.dto.seo.PageSeoResponse;
import com.bezkoder.spring.jpa.postgresql.dto.seo.PageSeoResponse;

public interface PageSeoService {

	List<PageSeoResponse> findAll();

	List<PageSeoResponse> findPublished();

	PageSeoResponse findById(Long id);

	PageSeoResponse create(PageSeoRequest request);

	PageSeoResponse update(Long id, PageSeoRequest request);

	void delete(Long id);

	PageSeoResponse findByPageKey(String pageKey);
}
