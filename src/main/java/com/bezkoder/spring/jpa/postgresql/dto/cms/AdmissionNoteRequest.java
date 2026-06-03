package com.bezkoder.spring.jpa.postgresql.dto.cms;

import jakarta.validation.constraints.NotBlank;

public class AdmissionNoteRequest {
	@NotBlank
	private String body;
	private String authorEmail;

	public String getBody() { return body; }
	public void setBody(String body) { this.body = body; }
	public String getAuthorEmail() { return authorEmail; }
	public void setAuthorEmail(String authorEmail) { this.authorEmail = authorEmail; }
}
