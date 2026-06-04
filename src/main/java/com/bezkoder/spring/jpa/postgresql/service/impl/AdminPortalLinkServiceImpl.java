package com.bezkoder.spring.jpa.postgresql.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bezkoder.spring.jpa.postgresql.dto.portal.AdminLinkResponse;
import com.bezkoder.spring.jpa.postgresql.entity.ParentStudentLink;
import com.bezkoder.spring.jpa.postgresql.exception.BadRequestException;
import com.bezkoder.spring.jpa.postgresql.exception.ResourceNotFoundException;
import com.bezkoder.spring.jpa.postgresql.repository.AdmissionApplicationRepository;
import com.bezkoder.spring.jpa.postgresql.repository.ParentStudentLinkRepository;
import com.bezkoder.spring.jpa.postgresql.repository.PortalUserRepository;
import com.bezkoder.spring.jpa.postgresql.service.AdminPortalLinkService;
import com.bezkoder.spring.jpa.postgresql.service.AuthAuditService;

@Service
public class AdminPortalLinkServiceImpl implements AdminPortalLinkService {

	private static final Set<String> ALLOWED_STATUSES = Set.of("APPROVED", "REJECTED", "PENDING");

	private final ParentStudentLinkRepository linkRepository;
	private final PortalUserRepository portalUserRepository;
	private final AdmissionApplicationRepository applicationRepository;
	private final AuthAuditService auditService;

	public AdminPortalLinkServiceImpl(
			ParentStudentLinkRepository linkRepository,
			PortalUserRepository portalUserRepository,
			AdmissionApplicationRepository applicationRepository,
			AuthAuditService auditService) {
		this.linkRepository = linkRepository;
		this.portalUserRepository = portalUserRepository;
		this.applicationRepository = applicationRepository;
		this.auditService = auditService;
	}

	@Override
	@Transactional(readOnly = true)
	public List<AdminLinkResponse> listAll() {
		List<AdminLinkResponse> result = new ArrayList<>();
		for (ParentStudentLink link : linkRepository.findAll()) {
			result.add(toDto(link));
		}
		return result;
	}

	@Override
	@Transactional
	public AdminLinkResponse setStatus(Long linkId, String status, String adminEmail) {
		String normalized = status == null ? "" : status.trim().toUpperCase();
		if (!ALLOWED_STATUSES.contains(normalized)) {
			throw new BadRequestException("Status must be APPROVED, REJECTED, or PENDING.");
		}

		ParentStudentLink link = linkRepository.findById(linkId)
				.orElseThrow(() -> new ResourceNotFoundException("Link not found."));
		link.setStatus(normalized);
		linkRepository.save(link);

		auditService.record("PORTAL_LINK_" + normalized, adminEmail,
				"Link " + linkId + " set to " + normalized);
		return toDto(link);
	}

	private AdminLinkResponse toDto(ParentStudentLink link) {
		AdminLinkResponse dto = new AdminLinkResponse();
		dto.setId(link.getId());
		dto.setRelationship(link.getRelationship());
		dto.setStatus(link.getStatus());

		portalUserRepository.findById(link.getPortalUserId()).ifPresent(user -> {
			dto.setPortalUserEmail(user.getEmail());
			dto.setPortalUserName(user.getFullName());
		});
		applicationRepository.findById(link.getApplicationId()).ifPresent(app -> {
			dto.setApplicationId(app.getApplicationId());
			dto.setStudentName(app.getFullName());
		});
		return dto;
	}
}
