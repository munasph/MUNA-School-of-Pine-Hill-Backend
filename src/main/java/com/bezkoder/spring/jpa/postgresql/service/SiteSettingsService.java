package com.bezkoder.spring.jpa.postgresql.service;

import com.bezkoder.spring.jpa.postgresql.dto.site.SiteSettingsRequest;
import com.bezkoder.spring.jpa.postgresql.dto.site.SiteSettingsResponse;

public interface SiteSettingsService {

	SiteSettingsResponse getSettings();

	SiteSettingsResponse updateSettings(SiteSettingsRequest request);
}
