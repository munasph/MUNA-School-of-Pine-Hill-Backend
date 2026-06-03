package com.bezkoder.spring.jpa.postgresql.service;

import java.util.List;

import com.bezkoder.spring.jpa.postgresql.dto.media.MediaAssetRequest;
import com.bezkoder.spring.jpa.postgresql.dto.media.MediaAssetResponse;

public interface MediaAssetService {

	List<MediaAssetResponse> findAll();

	List<MediaAssetResponse> findPublished();

	MediaAssetResponse findById(Long id);

	MediaAssetResponse create(MediaAssetRequest request);

	MediaAssetResponse update(Long id, MediaAssetRequest request);

	void delete(Long id);
}
