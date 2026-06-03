package com.bezkoder.spring.jpa.postgresql.dto.event;

import java.time.Instant;

import com.bezkoder.spring.jpa.postgresql.entity.enums.CmsPublishStatus;

public class EventResponse {

	private Long id;
	private String title;
	private String description;
	private String location;
	private Instant startAt;
	private Instant endAt;
	private boolean allDay;
	private CmsPublishStatus status;
	private Instant createdAt;
	private Instant updatedAt;

	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }
	public String getTitle() { return title; }
	public void setTitle(String title) { this.title = title; }
	public String getDescription() { return description; }
	public void setDescription(String description) { this.description = description; }
	public String getLocation() { return location; }
	public void setLocation(String location) { this.location = location; }
	public Instant getStartAt() { return startAt; }
	public void setStartAt(Instant startAt) { this.startAt = startAt; }
	public Instant getEndAt() { return endAt; }
	public void setEndAt(Instant endAt) { this.endAt = endAt; }
	public boolean getAllDay() { return allDay; }
	public void setAllDay(boolean allDay) { this.allDay = allDay; }
	public CmsPublishStatus getStatus() { return status; }
	public void setStatus(CmsPublishStatus status) { this.status = status; }
	public Instant getCreatedAt() { return createdAt; }
	public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
	public Instant getUpdatedAt() { return updatedAt; }
	public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}