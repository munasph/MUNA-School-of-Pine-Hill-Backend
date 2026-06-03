package com.bezkoder.spring.jpa.postgresql.dto.formfield;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class AdmissionFormFieldRequest {

	@NotBlank
	@Size(max = 100)
	private String fieldKey;

	@NotBlank
	@Size(max = 200)
	private String label;

	private boolean required;

	private boolean active;

	private int sortOrder;

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