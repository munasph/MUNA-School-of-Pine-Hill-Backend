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

/** Scaffold entity — `admission_form_fields` table. */
@Entity
@Table(name = "admission_form_fields")
public class AdmissionFormField {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

@Column(name = "field_key", length = 100, nullable = false)
	private String fieldKey;

@Column(name = "label", length = 200, nullable = false)
	private String label;

@Column(name = "required")
	private boolean required = false;

@Column(name = "active")
	private boolean active = true;

@Column(name = "sort_order")
	private int sortOrder;

	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }
	public String getFieldKey() { return fieldKey; }
	public void setFieldKey(String fieldKey) { this.fieldKey = fieldKey; }
	public String getLabel() { return label; }
	public void setLabel(String label) { this.label = label; }
	public boolean getRequired() { return required; }
	public void setRequired(boolean required) { this.required = required; }
	public boolean getActive() { return active; }
	public void setActive(boolean active) { this.active = active; }
	public int getSortOrder() { return sortOrder; }
	public void setSortOrder(int sortOrder) { this.sortOrder = sortOrder; }
}