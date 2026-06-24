package com.bezkoder.spring.jpa.postgresql.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bezkoder.spring.jpa.postgresql.dto.site.SiteSettingsRequest;
import com.bezkoder.spring.jpa.postgresql.dto.site.SiteSettingsResponse;
import com.bezkoder.spring.jpa.postgresql.entity.SiteSettings;
import com.bezkoder.spring.jpa.postgresql.repository.SiteSettingsRepository;
import com.bezkoder.spring.jpa.postgresql.service.SiteSettingsService;

@Service
@Transactional(readOnly = true)
public class SiteSettingsServiceImpl implements SiteSettingsService {

	private final SiteSettingsRepository siteSettingsRepository;

	public SiteSettingsServiceImpl(SiteSettingsRepository siteSettingsRepository) {
		this.siteSettingsRepository = siteSettingsRepository;
	}

	@Override
	public SiteSettingsResponse getSettings() {
		return toResponse(getOrCreateSettings());
	}

	@Override
	@Transactional
	public SiteSettingsResponse updateSettings(SiteSettingsRequest request) {
		SiteSettings entity = getOrCreateSettings();
		applyRequest(entity, request);
		return toResponse(siteSettingsRepository.save(entity));
	}

	private SiteSettings getOrCreateSettings() {
		return siteSettingsRepository.findById(SiteSettings.SINGLETON_ID)
				.orElseGet(() -> siteSettingsRepository.save(new SiteSettings()));
	}

	private void applyRequest(SiteSettings entity, SiteSettingsRequest request) {
		entity.setName(request.getName());
		entity.setShortName(request.getShortName());
		entity.setFoundedYear(request.getFoundedYear());
		entity.setAddress(request.getAddress());
		entity.setPhone(request.getPhone());
		entity.setEmail(request.getEmail());
		entity.setOfficeHours(request.getOfficeHours());
		entity.setBaseUrl(request.getBaseUrl());
		entity.setAdmissionsOpen(request.isAdmissionsOpen());
		entity.setAdmissionDocumentsRequired(request.isAdmissionDocumentsRequired());
	}

	private SiteSettingsResponse toResponse(SiteSettings entity) {
		SiteSettingsResponse response = new SiteSettingsResponse();
		response.setName(entity.getName());
		response.setShortName(entity.getShortName());
		response.setFoundedYear(entity.getFoundedYear());
		response.setAddress(entity.getAddress());
		response.setPhone(entity.getPhone());
		response.setEmail(entity.getEmail());
		response.setOfficeHours(entity.getOfficeHours());
		response.setBaseUrl(entity.getBaseUrl());
		response.setAdmissionsOpen(entity.isAdmissionsOpen());
		response.setAdmissionDocumentsRequired(entity.isAdmissionDocumentsRequired());
		return response;
	}
}
