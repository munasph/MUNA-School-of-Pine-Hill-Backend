package com.bezkoder.spring.jpa.postgresql.dto.auth;

import java.util.List;

/** Matches Angular {@code AuthResponse}. */
public class AuthResponse {

	private boolean success;
	private String message;
	private String token;
	private String email;
	private List<String> roles;
	/** True when credentials were valid but a TOTP code is still required. */
	private boolean mfaRequired;

	public AuthResponse() {
	}

	public AuthResponse(boolean success, String message) {
		this.success = success;
		this.message = message;
	}

	public static AuthResponse mfaChallenge() {
		AuthResponse response = new AuthResponse(false, "Enter your authentication code to continue.");
		response.setMfaRequired(true);
		return response;
	}

	public boolean isMfaRequired() {
		return mfaRequired;
	}

	public void setMfaRequired(boolean mfaRequired) {
		this.mfaRequired = mfaRequired;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
}
