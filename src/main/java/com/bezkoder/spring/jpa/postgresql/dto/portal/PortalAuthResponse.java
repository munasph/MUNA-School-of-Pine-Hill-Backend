package com.bezkoder.spring.jpa.postgresql.dto.portal;

/** Response for all portal auth endpoints (matches the Angular PortalAuthResponse). */
public class PortalAuthResponse {

	private boolean success;
	private String message;
	private String token;
	private String email;
	private String fullName;
	private String role;
	/** True when credentials were valid but a TOTP code is still required. */
	private boolean mfaRequired;

	public PortalAuthResponse() {
	}

	public PortalAuthResponse(boolean success, String message) {
		this.success = success;
		this.message = message;
	}

	public static PortalAuthResponse mfaChallenge() {
		PortalAuthResponse response = new PortalAuthResponse(false, "Enter your authentication code to continue.");
		response.setMfaRequired(true);
		return response;
	}

	public boolean isSuccess() { return success; }
	public void setSuccess(boolean success) { this.success = success; }
	public String getMessage() { return message; }
	public void setMessage(String message) { this.message = message; }
	public String getToken() { return token; }
	public void setToken(String token) { this.token = token; }
	public String getEmail() { return email; }
	public void setEmail(String email) { this.email = email; }
	public String getFullName() { return fullName; }
	public void setFullName(String fullName) { this.fullName = fullName; }
	public String getRole() { return role; }
	public void setRole(String role) { this.role = role; }
	public boolean isMfaRequired() { return mfaRequired; }
	public void setMfaRequired(boolean mfaRequired) { this.mfaRequired = mfaRequired; }
}
