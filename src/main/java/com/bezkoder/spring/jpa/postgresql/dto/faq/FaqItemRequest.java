package com.bezkoder.spring.jpa.postgresql.dto.faq;

import com.bezkoder.spring.jpa.postgresql.entity.enums.CmsPublishStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class FaqItemRequest {

	@NotBlank
	@Size(max = 500)
	private String question;

	@NotBlank
	private String answer;

	private String category;

	private int sortOrder;

	private CmsPublishStatus status;

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
}