package com.bezkoder.spring.jpa.postgresql.dto.faq;

import java.time.Instant;

import com.bezkoder.spring.jpa.postgresql.entity.enums.CmsPublishStatus;

public class FaqItemResponse {

	private Long id;
	private String question;
	private String answer;
	private String category;
	private int sortOrder;
	private CmsPublishStatus status;
	private Instant createdAt;
	private Instant updatedAt;

	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }
	public String getQuestion() { return question; }
	public void setQuestion(String question) { this.question = question; }
	public String getAnswer() { return answer; }
	public void setAnswer(String answer) { this.answer = answer; }
	public String getCategory() { return category; }
	public void setCategory(String category) { this.category = category; }
	public int getSortOrder() { return sortOrder; }
	public void setSortOrder(int sortOrder) { this.sortOrder = sortOrder; }
	public CmsPublishStatus getStatus() { return status; }
	public void setStatus(CmsPublishStatus status) { this.status = status; }
	public Instant getCreatedAt() { return createdAt; }
	public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
	public Instant getUpdatedAt() { return updatedAt; }
	public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}