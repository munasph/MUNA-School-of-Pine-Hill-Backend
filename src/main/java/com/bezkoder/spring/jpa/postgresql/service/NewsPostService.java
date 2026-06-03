package com.bezkoder.spring.jpa.postgresql.service;

import java.util.List;

import com.bezkoder.spring.jpa.postgresql.dto.news.NewsPostRequest;
import com.bezkoder.spring.jpa.postgresql.dto.news.NewsPostResponse;

public interface NewsPostService {

	List<NewsPostResponse> findAll();

	List<NewsPostResponse> findPublished();

	NewsPostResponse findById(Long id);

	NewsPostResponse create(NewsPostRequest request);

	NewsPostResponse update(Long id, NewsPostRequest request);

	void delete(Long id);
}
