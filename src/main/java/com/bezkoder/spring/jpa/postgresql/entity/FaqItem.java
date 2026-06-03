package com.bezkoder.spring.jpa.postgresql.entity;

import java.time.Instant;

import com.bezkoder.spring.jpa.postgresql.entity.enums.CmsPublishStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

/** Scaffold entity — `faq_items` table. */
@Entity
@Table(name = "faq_items")
public class FaqItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

@Column(name = "question", length = 500, nullable = false)
	private String question;

@Column(name = "answer", nullable = false)
	private String answer;

@Column(name = "category", length = 100)
	private String category;

@Column(name = "sort_order")
	private int sortOrder;

	@Enumerated(EnumType.STRING)
@Column(name = "status", length = 20, nullable = false)
	private CmsPublishStatus status = CmsPublishStatus.DRAFT;

	@Column(name = "created_at", nullable = false)
	private Instant createdAt;

	@Column(name = "updated_at", nullable = false)
	private Instant updatedAt;

	@PrePersist
	void onCreate() {
		Instant now = Instant.now();
		createdAt = now;
		updatedAt = now;
	}

	@PreUpdate
	void onUpdate() {
		updatedAt = Instant.now();
	}

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