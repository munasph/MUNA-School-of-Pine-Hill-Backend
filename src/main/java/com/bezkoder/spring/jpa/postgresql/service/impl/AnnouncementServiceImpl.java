package com.bezkoder.spring.jpa.postgresql.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bezkoder.spring.jpa.postgresql.dto.announcement.AnnouncementRequest;
import com.bezkoder.spring.jpa.postgresql.dto.announcement.AnnouncementResponse;
import com.bezkoder.spring.jpa.postgresql.entity.Announcement;
import com.bezkoder.spring.jpa.postgresql.exception.ResourceNotFoundException;
import com.bezkoder.spring.jpa.postgresql.repository.AnnouncementRepository;
import com.bezkoder.spring.jpa.postgresql.service.AnnouncementService;

@Service
@Transactional(readOnly = true)
public class AnnouncementServiceImpl implements AnnouncementService {

	private final AnnouncementRepository announcementRepository;

	public AnnouncementServiceImpl(AnnouncementRepository announcementRepository) {
		this.announcementRepository = announcementRepository;
	}

	@Override
	public List<AnnouncementResponse> getAllAnnouncements() {
		return announcementRepository.findAllByOrderByUpdatedAtDesc().stream()
				.map(this::toResponse)
				.toList();
	}

	@Override
	public List<AnnouncementResponse> getActiveAnnouncements() {
		return announcementRepository.findByActiveTrueOrderByUpdatedAtDesc().stream()
				.map(this::toResponse)
				.toList();
	}

	@Override
	public AnnouncementResponse getAnnouncementById(Long id) {
		return toResponse(findAnnouncementOrThrow(id));
	}

	@Override
	public AnnouncementResponse getActiveAnnouncementById(Long id) {
		Announcement entity = findAnnouncementOrThrow(id);
		if (!entity.isActive()) {
			throw new ResourceNotFoundException("Announcement not found with id: " + id);
		}
		return toResponse(entity);
	}

	@Override
	@Transactional
	public AnnouncementResponse createAnnouncement(AnnouncementRequest request) {
		return toResponse(announcementRepository.save(toEntity(new Announcement(), request)));
	}

	@Override
	@Transactional
	public AnnouncementResponse updateAnnouncement(Long id, AnnouncementRequest request) {
		Announcement entity = findAnnouncementOrThrow(id);
		return toResponse(announcementRepository.save(toEntity(entity, request)));
	}

	@Override
	@Transactional
	public void deleteAnnouncement(Long id) {
		if (!announcementRepository.existsById(id)) {
			throw new ResourceNotFoundException("Announcement not found with id: " + id);
		}
		announcementRepository.deleteById(id);
	}

	private Announcement findAnnouncementOrThrow(Long id) {
		return announcementRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Announcement not found with id: " + id));
	}

	private Announcement toEntity(Announcement entity, AnnouncementRequest request) {
		entity.setEmoji(request.getEmoji());
		entity.setTitle(request.getTitle());
		entity.setSubtitle(request.getSubtitle());
		entity.setBody(request.getBody());
		entity.setCta(request.getCta());
		entity.setHref(request.getHref());
		entity.setActive(request.isActive());
		return entity;
	}

	private AnnouncementResponse toResponse(Announcement entity) {
		AnnouncementResponse response = new AnnouncementResponse();
		response.setId(entity.getId());
		response.setEmoji(entity.getEmoji());
		response.setTitle(entity.getTitle());
		response.setSubtitle(entity.getSubtitle());
		response.setBody(entity.getBody());
		response.setCta(entity.getCta());
		response.setHref(entity.getHref());
		response.setActive(entity.isActive());
		response.setCreatedAt(entity.getCreatedAt());
		response.setUpdatedAt(entity.getUpdatedAt());
		return response;
	}
}
