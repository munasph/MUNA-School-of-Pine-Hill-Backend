package com.bezkoder.spring.jpa.postgresql.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import com.bezkoder.spring.jpa.postgresql.service.EventService;
import org.springframework.transaction.annotation.Transactional;

import com.bezkoder.spring.jpa.postgresql.dto.event.EventRequest;
import com.bezkoder.spring.jpa.postgresql.dto.event.EventResponse;
import com.bezkoder.spring.jpa.postgresql.entity.Event;
import com.bezkoder.spring.jpa.postgresql.exception.ResourceNotFoundException;
import com.bezkoder.spring.jpa.postgresql.repository.EventRepository;
import com.bezkoder.spring.jpa.postgresql.entity.enums.CmsPublishStatus;

@Service
@Transactional(readOnly = true)
public class EventServiceImpl implements EventService {

	private final EventRepository repository;

	public EventServiceImpl(EventRepository repository) {
		this.repository = repository;
	}

	@Override
	public List<EventResponse> findAll() {
		return repository.findAllByOrderByCreatedAtDesc().stream().map(this::toResponse).toList();
	}

	@Override
	public List<EventResponse> findPublished() {
		return repository.findAllByOrderByCreatedAtDesc().stream()
				.filter(e -> e.getStatus() == CmsPublishStatus.PUBLISHED)
				.map(this::toResponse)
				.toList();
	}

	@Override
	public EventResponse findById(Long id) {
		return toResponse(findOrThrow(id));
	}

	@Override
	@Transactional
	public EventResponse create(EventRequest request) {
		Event entity = new Event();
		applyRequest(entity, request);
		return toResponse(repository.save(entity));
	}

	@Override
	@Transactional
	public EventResponse update(Long id, EventRequest request) {
		Event entity = findOrThrow(id);
		applyRequest(entity, request);
		return toResponse(repository.save(entity));
	}

	@Override
	@Transactional
	public void delete(Long id) {
		if (!repository.existsById(id)) {
			throw new ResourceNotFoundException("Event not found with id: " + id);
		}
		repository.deleteById(id);
	}

	private Event findOrThrow(Long id) {
		return repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Event not found with id: " + id));
	}

	private void applyRequest(Event entity, EventRequest request) {
		entity.setTitle(request.getTitle());
		entity.setDescription(request.getDescription());
		entity.setLocation(request.getLocation());
		entity.setStartAt(request.getStartAt());
		entity.setEndAt(request.getEndAt());
		entity.setAllDay(request.getAllDay());
		entity.setStatus(request.getStatus());
	}

	private EventResponse toResponse(Event entity) {
		EventResponse r = new EventResponse();
		r.setId(entity.getId());
		r.setTitle(entity.getTitle());
		r.setDescription(entity.getDescription());
		r.setLocation(entity.getLocation());
		r.setStartAt(entity.getStartAt());
		r.setEndAt(entity.getEndAt());
		r.setAllDay(entity.getAllDay());
		r.setStatus(entity.getStatus());
		r.setCreatedAt(entity.getCreatedAt());
		r.setUpdatedAt(entity.getUpdatedAt());
		return r;
	}
}
