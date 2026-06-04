package com.bezkoder.spring.jpa.postgresql.dto.portal;

import jakarta.validation.constraints.NotBlank;

/** Used for email-verification confirmation (carries the token from the email link). */
public class TokenRequest {

	@NotBlank
	private String token;

	public String getToken() { return token; }
	public void setToken(String token) { this.token = token; }
}
