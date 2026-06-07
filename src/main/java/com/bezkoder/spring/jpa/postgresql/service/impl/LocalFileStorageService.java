package com.bezkoder.spring.jpa.postgresql.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.bezkoder.spring.jpa.postgresql.exception.BadRequestException;
import com.bezkoder.spring.jpa.postgresql.exception.ResourceNotFoundException;
import com.bezkoder.spring.jpa.postgresql.service.FileStorageService;

@Service
public class LocalFileStorageService implements FileStorageService {

	private static final Set<String> ALLOWED_EXTENSIONS = Set.of(
			".pdf", ".doc", ".docx", ".jpg", ".jpeg", ".png");

	private final Path uploadRoot;

	public LocalFileStorageService(@Value("${app.upload.dir:./uploads}") String uploadDir) {
		this.uploadRoot = Paths.get(uploadDir).toAbsolutePath().normalize();
	}

	@Override
	public String storeAdmissionDocument(Long applicationId, String docType, MultipartFile file) {
		if (file == null || file.isEmpty()) {
			throw new BadRequestException("Uploaded file is empty.");
		}

		String originalName = StringUtils.cleanPath(file.getOriginalFilename() != null
				? file.getOriginalFilename()
				: "document");
		String extension = extensionOf(originalName);
		if (!ALLOWED_EXTENSIONS.contains(extension)) {
			throw new BadRequestException("File type not allowed. Use PDF, DOC, DOCX, JPG, or PNG.");
		}

		String storedName = docType.toLowerCase(Locale.ROOT) + "_" + UUID.randomUUID() + extension;
		Path targetDir = uploadRoot.resolve("admissions").resolve(String.valueOf(applicationId));

		try {
			Files.createDirectories(targetDir);
			Path target = targetDir.resolve(storedName);
			file.transferTo(target);
			return uploadRoot.relativize(target).toString().replace('\\', '/');
		} catch (IOException ex) {
			throw new BadRequestException("Could not store uploaded file.");
		}
	}

	@Override
	public Resource loadAsResource(String storedPath) {
		try {
			Path file = uploadRoot.resolve(storedPath).normalize();
			if (!file.startsWith(uploadRoot) || !Files.exists(file)) {
				throw new ResourceNotFoundException("File not found.");
			}
			Resource resource = new UrlResource(file.toUri());
			if (!resource.exists() || !resource.isReadable()) {
				throw new ResourceNotFoundException("File not found.");
			}
			return resource;
		} catch (IOException ex) {
			throw new ResourceNotFoundException("File not found.");
		}
	}

	@Override
	public void deleteStoredFile(String storedPath) {
		if (storedPath == null || storedPath.isBlank()) {
			return;
		}
		try {
			Path file = uploadRoot.resolve(storedPath).normalize();
			if (file.startsWith(uploadRoot)) {
				Files.deleteIfExists(file);
			}
		} catch (IOException ignored) {
			// Best-effort cleanup only.
		}
	}

	private static String extensionOf(String filename) {
		int dot = filename.lastIndexOf('.');
		if (dot < 0) {
			return "";
		}
		return filename.substring(dot).toLowerCase(Locale.ROOT);
	}
}
