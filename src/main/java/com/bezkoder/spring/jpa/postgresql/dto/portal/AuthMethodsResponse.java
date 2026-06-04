package com.bezkoder.spring.jpa.postgresql.dto.portal;

/** Which portal login methods are enabled — the frontend renders options from this. */
public class AuthMethodsResponse {

	private boolean password;
	private boolean magicLink;
	private boolean google;
	/** Only populated when Google is enabled. */
	private String googleClientId;

	public boolean isPassword() { return password; }
	public void setPassword(boolean password) { this.password = password; }
	public boolean isMagicLink() { return magicLink; }
	public void setMagicLink(boolean magicLink) { this.magicLink = magicLink; }
	public boolean isGoogle() { return google; }
	public void setGoogle(boolean google) { this.google = google; }
	public String getGoogleClientId() { return googleClientId; }
	public void setGoogleClientId(String googleClientId) { this.googleClientId = googleClientId; }
}
