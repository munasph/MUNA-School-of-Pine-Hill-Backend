package com.bezkoder.spring.jpa.postgresql.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bezkoder.spring.jpa.postgresql.dto.announcement.AnnouncementRequest;
import com.bezkoder.spring.jpa.postgresql.dto.announcement.AnnouncementResponse;
import com.bezkoder.spring.jpa.postgresql.exception.ResourceNotFoundException;
import com.bezkoder.spring.jpa.postgresql.mapper.EntityMapper;
import com.bezkoder.spring.jpa.postgresql.model.Announcement;
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
				.map(EntityMapper::toAnnouncementResponse)
				.toList();
	}

	@Override
	public List<AnnouncementResponse> getActiveAnnouncements() {
		return announcementRepository.findByActiveTrueOrderByUpdatedAtDesc().stream()
				.map(EntityMapper::toAnnouncementResponse)
				.toList();
	}

	@Override
	public AnnouncementResponse getAnnouncementById(Long id) {
		Announcement entity = announcementRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Announcement not found with id: " + id));
		return EntityMapper.toAnnouncementResponse(entity);
	}

	@Override
	@Transactional
	public AnnouncementResponse createAnnouncement(AnnouncementRequest request) {
		Announcement entity = mapRequestToEntity(new Announcement(), request);
		return EntityMapper.toAnnouncementResponse(announcementRepository.save(entity));
	}

	@Override
	@Transactional
	public AnnouncementResponse updateAnnouncement(Long id, AnnouncementRequest request) {
		Announcement entity = announcementRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Announcement not found with id: " + id));
		mapRequestToEntity(entity, request);
		return EntityMapper.toAnnouncementResponse(announcementRepository.save(entity));
	}

	@Override
	@Transactional
	public void deleteAnnouncement(Long id) {
		if (!announcementRepository.existsById(id)) {
			throw new ResourceNotFoundException("Announcement not found with id: " + id);
		}
		announcementRepository.deleteById(id);
	}

	private Announcement mapRequestToEntity(Announcement entity, AnnouncementRequest request) {
		entity.setEmoji(request.getEmoji());
		entity.setTitle(request.getTitle());
		entity.setSubtitle(request.getSubtitle());
		entity.setCta(request.getCta());
		entity.setHref(request.getHref());
		entity.setActive(request.isActive());
		return entity;
	}
}
