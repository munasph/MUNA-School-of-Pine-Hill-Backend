package com.bezkoder.spring.jpa.postgresql.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.bezkoder.spring.jpa.postgresql.admission.AdmissionDocumentType;
import com.bezkoder.spring.jpa.postgresql.dto.admission.AdmissionResponse;
import com.bezkoder.spring.jpa.postgresql.dto.admission.AdmissionSubmitResponse;
import com.bezkoder.spring.jpa.postgresql.dto.admission.CreateAdmissionRequest;
import com.bezkoder.spring.jpa.postgresql.dto.admission.UpdateAdmissionStatusRequest;
import com.bezkoder.spring.jpa.postgresql.entity.AdmissionApplication;
import com.bezkoder.spring.jpa.postgresql.entity.AdmissionDocument;
import com.bezkoder.spring.jpa.postgresql.entity.SiteSettings;
import com.bezkoder.spring.jpa.postgresql.entity.enums.ApplicationStatus;
import com.bezkoder.spring.jpa.postgresql.exception.BadRequestException;
import com.bezkoder.spring.jpa.postgresql.exception.ResourceNotFoundException;
import com.bezkoder.spring.jpa.postgresql.repository.AdmissionApplicationRepository;
import com.bezkoder.spring.jpa.postgresql.repository.AdmissionDocumentRepository;
import com.bezkoder.spring.jpa.postgresql.repository.SiteSettingsRepository;
import com.bezkoder.spring.jpa.postgresql.service.AdmissionService;
import com.bezkoder.spring.jpa.postgresql.service.FileStorageService;

@Service
@Transactional(readOnly = true)
public class AdmissionServiceImpl implements AdmissionService {

	private final AdmissionApplicationRepository admissionRepository;
	private final SiteSettingsRepository siteSettingsRepository;
	private final AdmissionDocumentRepository documentRepository;
	private final FileStorageService fileStorageService;

	public AdmissionServiceImpl(
			AdmissionApplicationRepository admissionRepository,
			SiteSettingsRepository siteSettingsRepository,
			AdmissionDocumentRepository documentRepository,
			FileStorageService fileStorageService) {
		this.admissionRepository = admissionRepository;
		this.siteSettingsRepository = siteSettingsRepository;
		this.documentRepository = documentRepository;
		this.fileStorageService = fileStorageService;
	}

	@Override
	@Transactional
	public AdmissionSubmitResponse submitApplication(CreateAdmissionRequest request) {
		assertAdmissionsOpen();

		AdmissionApplication saved = admissionRepository.save(toEntity(request));

		return new AdmissionSubmitResponse(
				true,
				saved.getApplicationId(),
				"Application received.");
	}

	@Override
	@Transactional
	public AdmissionSubmitResponse submitApplicationWithDocuments(
			CreateAdmissionRequest request,
			List<MultipartFile> files,
			List<String> docTypes) {
		assertAdmissionsOpen();
		validateDocumentUpload(files, docTypes);

		AdmissionApplication saved = admissionRepository.save(toEntity(request));
		if (files != null && !files.isEmpty()) {
			saveDocuments(saved.getId(), files, docTypes);
		}

		String message = (files == null || files.isEmpty())
				? "Application received."
				: "Registration and documents received.";
		return new AdmissionSubmitResponse(
				true,
				saved.getApplicationId(),
				message);
	}

	private void assertAdmissionsOpen() {
		SiteSettings settings = siteSettingsRepository.findById(SiteSettings.SINGLETON_ID)
				.orElse(new SiteSettings());
		if (!settings.isAdmissionsOpen()) {
			throw new BadRequestException("Admission applications are currently closed.");
		}
	}

	private void validateDocumentUpload(List<MultipartFile> files, List<String> docTypes) {
		if (files == null || files.isEmpty()) {
			return;
		}
		if (docTypes == null || docTypes.size() != files.size()) {
			throw new BadRequestException("Document upload metadata is invalid.");
		}

		Set<String> uploadedTypes = new HashSet<>();
		for (int i = 0; i < docTypes.size(); i++) {
			String docType = docTypes.get(i);
			if (docType == null || docType.isBlank()) {
				throw new BadRequestException("Document type is required for each upload.");
			}
			if (!AdmissionDocumentType.ALLOWED.contains(docType)) {
				throw new BadRequestException("Unsupported document type: " + docType);
			}
			if (!uploadedTypes.add(docType)) {
				throw new BadRequestException("Duplicate document upload: " + docType);
			}
			MultipartFile file = files.get(i);
			if (file == null || file.isEmpty()) {
				throw new BadRequestException("Uploaded file is empty.");
			}
		}
	}

	private void saveDocuments(Long applicationId, List<MultipartFile> files, List<String> docTypes) {
		for (int i = 0; i < files.size(); i++) {
			String docType = docTypes.get(i);
			MultipartFile file = files.get(i);
			String storedPath = fileStorageService.storeAdmissionDocument(applicationId, docType, file);

			AdmissionDocument entity = new AdmissionDocument();
			entity.setApplicationId(applicationId);
			entity.setDocType(docType);
			entity.setFileName(file.getOriginalFilename() != null ? file.getOriginalFilename() : docType);
			entity.setFileUrl(storedPath);
			documentRepository.save(entity);
		}
	}

	@Override
	public List<AdmissionResponse> getAllApplications() {
		return admissionRepository.findAllByOrderBySubmittedAtDesc().stream()
				.map(this::toResponse)
				.toList();
	}

	@Override
	public List<AdmissionResponse> getApplicationsByStatus(ApplicationStatus status) {
		return admissionRepository.findAllByOrderBySubmittedAtDesc().stream()
				.filter(app -> app.getStatus() == status)
				.map(this::toResponse)
				.toList();
	}

	@Override
	public AdmissionResponse getApplicationById(Long id) {
		return toResponse(findApplicationOrThrow(id));
	}

	@Override
	@Transactional
	public AdmissionResponse updateApplicationStatus(Long id, UpdateAdmissionStatusRequest request) {
		AdmissionApplication entity = findApplicationOrThrow(id);
		entity.setStatus(request.getStatus());
		return toResponse(admissionRepository.save(entity));
	}

	@Override
	@Transactional
	public void deleteApplication(Long id) {
		if (!admissionRepository.existsById(id)) {
			throw new ResourceNotFoundException("Application not found with id: " + id);
		}
		admissionRepository.deleteById(id);
	}

	@Override
	public String exportApplicationsAsCsv(ApplicationStatus status) {
		List<AdmissionResponse> applications = status == null
				? getAllApplications()
				: getApplicationsByStatus(status);

		StringBuilder csv = new StringBuilder();
		csv.append("Application ID,First Name,Last Name,Date of Birth,Grade,Gender,Street,City,State,ZIP,Parent 1 Name,Parent 1 Phone,Parent 1 Email,Parent 2 Name,Parent 2 Phone,Parent 2 Email,Status,Submitted At\n");

		for (AdmissionResponse app : applications) {
			csv.append(csvCell(app.getApplicationId())).append(',')
					.append(csvCell(app.getFirstName())).append(',')
					.append(csvCell(app.getLastName())).append(',')
					.append(csvCell(app.getDob() != null ? app.getDob().toString() : "")).append(',')
					.append(csvCell(app.getClassApplying())).append(',')
					.append(csvCell(app.getGender())).append(',')
					.append(csvCell(app.getStreetAddress())).append(',')
					.append(csvCell(app.getCity())).append(',')
					.append(csvCell(app.getState())).append(',')
					.append(csvCell(app.getZip())).append(',')
					.append(csvCell(app.getParentName())).append(',')
					.append(csvCell(app.getParentPhone())).append(',')
					.append(csvCell(app.getParent1Email())).append(',')
					.append(csvCell(app.getParent2Name())).append(',')
					.append(csvCell(app.getParent2Phone())).append(',')
					.append(csvCell(app.getParent2Email())).append(',')
					.append(csvCell(app.getStatus() != null ? app.getStatus().name() : "")).append(',')
					.append(csvCell(app.getSubmittedAt() != null ? app.getSubmittedAt().toString() : ""))
					.append('\n');
		}

		return csv.toString();
	}

	private static String csvCell(String value) {
		if (value == null) {
			return "\"\"";
		}
		return "\"" + value.replace("\"", "\"\"") + "\"";
	}

	private AdmissionApplication findApplicationOrThrow(Long id) {
		return admissionRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Application not found with id: " + id));
	}

	private AdmissionApplication toEntity(CreateAdmissionRequest request) {
		AdmissionApplication entity = new AdmissionApplication();
		entity.setApplicationId("APP-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
		entity.setFirstName(request.getFirstName());
		entity.setLastName(request.getLastName());
		entity.setFullName(request.getFirstName() + " " + request.getLastName());
		entity.setDob(request.getDob());
		entity.setClassApplying(request.getClassApplying());
		entity.setGender(request.getGender());
		entity.setStreetAddress(request.getStreetAddress());
		entity.setCity(request.getCity());
		entity.setState(request.getState());
		entity.setZip(request.getZip());
		entity.setParentName(request.getParent1Name());
		entity.setParentPhone(request.getParent1Phone());
		entity.setParent1Email(request.getParent1Email());
		entity.setParent2Name(blankToNull(request.getParent2Name()));
		entity.setParent2Phone(blankToNull(request.getParent2Phone()));
		entity.setParent2Email(blankToNull(request.getParent2Email()));
		entity.setStatus(ApplicationStatus.PENDING);
		return entity;
	}

	private static String blankToNull(String value) {
		return value == null || value.isBlank() ? null : value.trim();
	}

	private AdmissionResponse toResponse(AdmissionApplication entity) {
		AdmissionResponse response = new AdmissionResponse();
		response.setId(entity.getId());
		response.setApplicationId(entity.getApplicationId());
		response.setFullName(entity.getFullName());
		response.setFirstName(entity.getFirstName());
		response.setLastName(entity.getLastName());
		response.setDob(entity.getDob());
		response.setClassApplying(entity.getClassApplying());
		response.setGender(entity.getGender());
		response.setStreetAddress(entity.getStreetAddress());
		response.setCity(entity.getCity());
		response.setState(entity.getState());
		response.setZip(entity.getZip());
		response.setParentName(entity.getParentName());
		response.setParentPhone(entity.getParentPhone());
		response.setParent1Email(entity.getParent1Email());
		response.setParent2Name(entity.getParent2Name());
		response.setParent2Phone(entity.getParent2Phone());
		response.setParent2Email(entity.getParent2Email());
		response.setStatus(entity.getStatus());
		response.setSubmittedAt(entity.getSubmittedAt());
		return response;
	}
}
