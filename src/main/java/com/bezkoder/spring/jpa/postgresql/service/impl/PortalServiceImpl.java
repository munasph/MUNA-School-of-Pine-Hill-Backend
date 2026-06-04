package com.bezkoder.spring.jpa.postgresql.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bezkoder.spring.jpa.postgresql.dto.portal.LinkRequestPayload;
import com.bezkoder.spring.jpa.postgresql.dto.portal.LinkedStudentResponse;
import com.bezkoder.spring.jpa.postgresql.dto.portal.PortalProfileResponse;
import com.bezkoder.spring.jpa.postgresql.entity.AdmissionApplication;
import com.bezkoder.spring.jpa.postgresql.entity.ParentStudentLink;
import com.bezkoder.spring.jpa.postgresql.entity.PortalUser;
import com.bezkoder.spring.jpa.postgresql.exception.BadRequestException;
import com.bezkoder.spring.jpa.postgresql.exception.ResourceNotFoundException;
import com.bezkoder.spring.jpa.postgresql.exception.UnauthorizedException;
import com.bezkoder.spring.jpa.postgresql.repository.AdmissionApplicationRepository;
import com.bezkoder.spring.jpa.postgresql.repository.ParentStudentLinkRepository;
import com.bezkoder.spring.jpa.postgresql.repository.PortalUserRepository;
import com.bezkoder.spring.jpa.postgresql.service.PortalService;

/**
 * Read/write access to a portal user's own profile and linked students. Crucially,
 * student details are only resolved for APPROVED links — a PENDING link reveals nothing
 * beyond the fact that the request is awaiting admin approval.
 */
@Service
public class PortalServiceImpl implements PortalService {

	private static final String STATUS_PENDING = "PENDING";
	private static final String STATUS_APPROVED = "APPROVED";

	private final PortalUserRepository portalUserRepository;
	private final ParentStudentLinkRepository linkRepository;
	private final AdmissionApplicationRepository applicationRepository;

	public PortalServiceImpl(
			PortalUserRepository portalUserRepository,
			ParentStudentLinkRepository linkRepository,
			AdmissionApplicationRepository applicationRepository) {
		this.portalUserRepository = portalUserRepository;
		this.linkRepository = linkRepository;
		this.applicationRepository = applicationRepository;
	}

	@Override
	@Transactional(readOnly = true)
	public PortalProfileResponse getProfile(String email) {
		PortalUser user = requireUser(email);
		return toProfile(user);
	}

	@Override
	@Transactional
	public PortalProfileResponse requestLink(String email, LinkRequestPayload payload) {
		PortalUser user = requireUser(email);

		AdmissionApplication application = applicationRepository
				.findByApplicationIdIgnoreCase(payload.getApplicationId().trim())
				.orElseThrow(() -> new ResourceNotFoundException(
						"No student record found for that application ID."));

		if (linkRepository.existsByPortalUserIdAndApplicationId(user.getId(), application.getId())) {
			throw new BadRequestException("You have already requested access to this student.");
		}

		ParentStudentLink link = new ParentStudentLink();
		link.setPortalUserId(user.getId());
		link.setApplicationId(application.getId());
		link.setRelationship(payload.getRelationship() == null || payload.getRelationship().isBlank()
				? user.getRole().name()
				: payload.getRelationship().trim());
		link.setStatus(STATUS_PENDING);
		linkRepository.save(link);

		return toProfile(user);
	}

	private PortalProfileResponse toProfile(PortalUser user) {
		List<LinkedStudentResponse> students = new ArrayList<>();
		for (ParentStudentLink link : linkRepository.findByPortalUserId(user.getId())) {
			LinkedStudentResponse dto = new LinkedStudentResponse();
			dto.setLinkId(link.getId());
			dto.setRelationship(link.getRelationship());
			dto.setLinkStatus(link.getStatus());

			if (STATUS_APPROVED.equalsIgnoreCase(link.getStatus())) {
				applicationRepository.findById(link.getApplicationId()).ifPresent(app -> {
					dto.setApplicationId(app.getApplicationId());
					dto.setStudentName(app.getFullName());
					dto.setClassApplying(app.getClassApplying());
					dto.setApplicationStatus(app.getStatus().name());
				});
			} else {
				dto.setStudentName("Awaiting approval");
			}
			students.add(dto);
		}

		PortalProfileResponse profile = new PortalProfileResponse();
		profile.setEmail(user.getEmail());
		profile.setFullName(user.getFullName());
		profile.setRole(user.getRole().name());
		profile.setEmailVerified(user.isEmailVerified());
		profile.setMfaEnabled(user.isMfaEnabled());
		profile.setStudents(students);
		return profile;
	}

	private PortalUser requireUser(String email) {
		if (email == null) {
			throw new UnauthorizedException("Not authenticated.");
		}
		return portalUserRepository.findByEmailIgnoreCase(email)
				.orElseThrow(() -> new UnauthorizedException("Account not found."));
	}
}
