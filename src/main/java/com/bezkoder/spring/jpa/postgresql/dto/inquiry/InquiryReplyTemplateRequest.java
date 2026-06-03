package com.bezkoder.spring.jpa.postgresql.dto.inquiry;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class InquiryReplyTemplateRequest {

	@NotBlank
	@Size(max = 200)
	private String name;

	@NotBlank
	@Size(max = 300)
	private String subject;

	@NotBlank
	private String body;

	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	public String getSubject() { return subject; }
	public void setSubject(String subject) { this.subject = subject; }
	public String getBody() { return body; }
	public void setBody(String body) { this.body = body; }
}