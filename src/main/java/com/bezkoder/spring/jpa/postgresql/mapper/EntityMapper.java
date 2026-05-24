package com.bezkoder.spring.jpa.postgresql.mapper;

import com.bezkoder.spring.jpa.postgresql.dto.admission.AdmissionResponse;
import com.bezkoder.spring.jpa.postgresql.dto.announcement.AnnouncementResponse;
import com.bezkoder.spring.jpa.postgresql.dto.site.SiteSettingsResponse;
import com.bezkoder.spring.jpa.postgresql.model.AdmissionApplication;
import com.bezkoder.spring.jpa.postgresql.model.Announcement;
import com.bezkoder.spring.jpa.postgresql.model.SiteSettings;

public final class EntityMapper {

	private EntityMapper() {
	}

	public static AdmissionResponse toAdmissionResponse(AdmissionApplication entity) {
		AdmissionResponse dto = new AdmissionResponse();
		dto.setId(entity.getId());
		dto.setApplicationId(entity.getApplicationId());
		dto.setFullName(entity.getFullName());
		dto.setDob(entity.getDob());
		dto.setClassApplying(entity.getClassApplying());
		dto.setGender(entity.getGender());
		dto.setParentName(entity.getParentName());
		dto.setParentPhone(entity.getParentPhone());
		dto.setStatus(entity.getStatus());
		dto.setSubmittedAt(entity.getSubmittedAt());
		return dto;
	}

	public static AnnouncementResponse toAnnouncementResponse(Announcement entity) {
		AnnouncementResponse dto = new AnnouncementResponse();
		dto.setId(entity.getId());
		dto.setEmoji(entity.getEmoji());
		dto.setTitle(entity.getTitle());
		dto.setSubtitle(entity.getSubtitle());
		dto.setCta(entity.getCta());
		dto.setHref(entity.getHref());
		dto.setActive(entity.isActive());
		dto.setCreatedAt(entity.getCreatedAt());
		dto.setUpdatedAt(entity.getUpdatedAt());
		return dto;
	}

	public static SiteSettingsResponse toSiteSettingsResponse(SiteSettings entity) {
		SiteSettingsResponse dto = new SiteSettingsResponse();
		dto.setName(entity.getName());
		dto.setShortName(entity.getShortName());
		dto.setFoundedYear(entity.getFoundedYear());
		dto.setAddress(entity.getAddress());
		dto.setPhone(entity.getPhone());
		dto.setEmail(entity.getEmail());
		dto.setOfficeHours(entity.getOfficeHours());
		dto.setBaseUrl(entity.getBaseUrl());
		return dto;
	}
}
