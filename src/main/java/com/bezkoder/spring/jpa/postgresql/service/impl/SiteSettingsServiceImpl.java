package com.bezkoder.spring.jpa.postgresql.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bezkoder.spring.jpa.postgresql.dto.site.SiteSettingsRequest;
import com.bezkoder.spring.jpa.postgresql.dto.site.SiteSettingsResponse;
import com.bezkoder.spring.jpa.postgresql.mapper.EntityMapper;
import com.bezkoder.spring.jpa.postgresql.model.SiteSettings;
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
		return EntityMapper.toSiteSettingsResponse(getOrCreateSettings());
	}

	@Override
	@Transactional
	public SiteSettingsResponse updateSettings(SiteSettingsRequest request) {
		SiteSettings entity = getOrCreateSettings();
		entity.setName(request.getName());
		entity.setShortName(request.getShortName());
		entity.setFoundedYear(request.getFoundedYear());
		entity.setAddress(request.getAddress());
		entity.setPhone(request.getPhone());
		entity.setEmail(request.getEmail());
		entity.setOfficeHours(request.getOfficeHours());
		entity.setBaseUrl(request.getBaseUrl());
		return EntityMapper.toSiteSettingsResponse(siteSettingsRepository.save(entity));
	}

	private SiteSettings getOrCreateSettings() {
		return siteSettingsRepository.findById(SiteSettings.SINGLETON_ID)
				.orElseGet(() -> siteSettingsRepository.save(new SiteSettings()));
	}
}
