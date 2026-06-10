package com.bezkoder.spring.jpa.postgresql.service;

import java.util.List;

import com.bezkoder.spring.jpa.postgresql.dto.announcement.AnnouncementRequest;
import com.bezkoder.spring.jpa.postgresql.dto.announcement.AnnouncementResponse;

public interface AnnouncementService {

	List<AnnouncementResponse> getAllAnnouncements();

	List<AnnouncementResponse> getActiveAnnouncements();

	AnnouncementResponse getAnnouncementById(Long id);

	AnnouncementResponse getActiveAnnouncementById(Long id);

	AnnouncementResponse createAnnouncement(AnnouncementRequest request);

	AnnouncementResponse updateAnnouncement(Long id, AnnouncementRequest request);

	void deleteAnnouncement(Long id);
}
