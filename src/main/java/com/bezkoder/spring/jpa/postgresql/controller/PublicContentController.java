package com.bezkoder.spring.jpa.postgresql.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bezkoder.spring.jpa.postgresql.dto.announcement.AnnouncementResponse;
import com.bezkoder.spring.jpa.postgresql.dto.site.SiteSettingsResponse;
import com.bezkoder.spring.jpa.postgresql.service.AnnouncementService;
import com.bezkoder.spring.jpa.postgresql.service.SiteSettingsService;

/** Public read-only CMS endpoints for the Angular public site. */
@RestController
@RequestMapping("/api")
public class PublicContentController {

	private final AnnouncementService announcementService;
	private final SiteSettingsService siteSettingsService;

	public PublicContentController(
			AnnouncementService announcementService,
			SiteSettingsService siteSettingsService) {
		this.announcementService = announcementService;
		this.siteSettingsService = siteSettingsService;
	}

	@GetMapping("/announcements/active")
	public ResponseEntity<List<AnnouncementResponse>> getActiveAnnouncements() {
		return ResponseEntity.ok(announcementService.getActiveAnnouncements());
	}

	@GetMapping("/announcements/{id}")
	public ResponseEntity<AnnouncementResponse> getActiveAnnouncement(@PathVariable Long id) {
		return ResponseEntity.ok(announcementService.getActiveAnnouncementById(id));
	}

	@GetMapping("/site-settings")
	public ResponseEntity<SiteSettingsResponse> getSiteSettings() {
		return ResponseEntity.ok(siteSettingsService.getSettings());
	}
}
