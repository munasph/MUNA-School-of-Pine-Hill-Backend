package com.bezkoder.spring.jpa.postgresql.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {

	String storeAdmissionDocument(Long applicationId, String docType, MultipartFile file);

	Resource loadAsResource(String storedPath);

	void deleteStoredFile(String storedPath);
}
