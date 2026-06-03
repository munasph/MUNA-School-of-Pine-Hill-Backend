package com.bezkoder.spring.jpa.postgresql.service;

import java.util.List;

import com.bezkoder.spring.jpa.postgresql.dto.event.EventRequest;
import com.bezkoder.spring.jpa.postgresql.dto.event.EventResponse;

public interface EventService {

	List<EventResponse> findAll();

	List<EventResponse> findPublished();

	EventResponse findById(Long id);

	EventResponse create(EventRequest request);

	EventResponse update(Long id, EventRequest request);

	void delete(Long id);
}
