package com.bezkoder.spring.jpa.postgresql.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.bezkoder.spring.jpa.postgresql.entity.SiteSettings;
import com.bezkoder.spring.jpa.postgresql.repository.SiteSettingsRepository;

@Component
public class SiteSettingsSeeder implements ApplicationRunner {

	private static final Logger log = LoggerFactory.getLogger(SiteSettingsSeeder.class);

	private final SiteSettingsRepository siteSettingsRepository;

	public SiteSettingsSeeder(SiteSettingsRepository siteSettingsRepository) {
		this.siteSettingsRepository = siteSettingsRepository;
	}

	@Override
	public void run(ApplicationArguments args) {
		SiteSettings settings = siteSettingsRepository.findById(SiteSettings.SINGLETON_ID)
				.orElseGet(() -> siteSettingsRepository.save(new SiteSettings()));

		SiteSettings defaults = new SiteSettings();
		boolean changed = false;

		if (isPlaceholder(settings.getName(), "School Name")) {
			settings.setName(defaults.getName());
			changed = true;
		}
		if (isPlaceholder(settings.getShortName(), "School")) {
			settings.setShortName(defaults.getShortName());
			changed = true;
		}
		if (isPlaceholder(settings.getFoundedYear(), "0000")) {
			settings.setFoundedYear(defaults.getFoundedYear());
			changed = true;
		}
		if (isPlaceholder(settings.getAddress(), "Street Address, City, State, Country")) {
			settings.setAddress(defaults.getAddress());
			changed = true;
		}
		if (isPlaceholder(settings.getPhone(), "Phone Number")) {
			settings.setPhone(defaults.getPhone());
			changed = true;
		}
		if (isPlaceholder(settings.getEmail(), "email@example.com")) {
			settings.setEmail(defaults.getEmail());
			changed = true;
		}
		if (isPlaceholder(settings.getBaseUrl(), "https://example.com")) {
			settings.setBaseUrl(defaults.getBaseUrl());
			changed = true;
		}

		if (changed) {
			siteSettingsRepository.save(settings);
			log.info("Updated legacy site_settings placeholders with MUNA School defaults.");
		}
	}

	private boolean isPlaceholder(String value, String placeholder) {
		return value == null || value.isBlank() || placeholder.equals(value.trim());
	}
}
