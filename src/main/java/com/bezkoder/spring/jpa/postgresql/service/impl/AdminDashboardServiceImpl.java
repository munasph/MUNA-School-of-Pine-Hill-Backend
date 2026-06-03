package com.bezkoder.spring.jpa.postgresql.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bezkoder.spring.jpa.postgresql.dto.admin.AdminDashboardResponse;
import com.bezkoder.spring.jpa.postgresql.entity.enums.ApplicationStatus;
import com.bezkoder.spring.jpa.postgresql.entity.enums.ContactInquiryStatus;
import com.bezkoder.spring.jpa.postgresql.repository.AdmissionApplicationRepository;
import com.bezkoder.spring.jpa.postgresql.repository.AnnouncementRepository;
import com.bezkoder.spring.jpa.postgresql.repository.ContactInquiryRepository;
import com.bezkoder.spring.jpa.postgresql.service.AdminDashboardService;

@Service
@Transactional(readOnly = true)
public class AdminDashboardServiceImpl implements AdminDashboardService {

	private final AdmissionApplicationRepository admissionRepository;
	private final AnnouncementRepository announcementRepository;
	private final ContactInquiryRepository contactRepository;

	public AdminDashboardServiceImpl(
			AdmissionApplicationRepository admissionRepository,
			AnnouncementRepository announcementRepository,
			ContactInquiryRepository contactRepository) {
		this.admissionRepository = admissionRepository;
		this.announcementRepository = announcementRepository;
		this.contactRepository = contactRepository;
	}

	@Override
	public AdminDashboardResponse getDashboardSummary() {
		AdminDashboardResponse response = new AdminDashboardResponse();
		response.setTotalApplications(admissionRepository.count());
		response.setPendingApplications(admissionRepository.countByStatus(ApplicationStatus.PENDING));
		response.setActiveAnnouncements(announcementRepository.countByActiveTrue());
		response.setNewContactMessages(contactRepository.countByStatus(ContactInquiryStatus.NEW));
		return response;
	}
}
