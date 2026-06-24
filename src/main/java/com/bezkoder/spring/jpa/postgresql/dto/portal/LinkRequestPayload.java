package com.bezkoder.spring.jpa.postgresql.dto.portal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/** Portal user asks to be linked to a student via the admission application ID. */
public class LinkRequestPayload {

	@NotBlank
	@Size(max = 64)
	private String applicationId;

	@Size(max = 50)
	private String relationship;

	public String getApplicationId() { return applicationId; }
	public void setApplicationId(String applicationId) { this.applicationId = applicationId; }
	public String getRelationship() { return relationship; }
	public void setRelationship(String relationship) { this.relationship = relationship; }
}
