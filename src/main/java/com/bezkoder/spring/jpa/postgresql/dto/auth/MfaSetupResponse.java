package com.bezkoder.spring.jpa.postgresql.dto.auth;

/** Returned when starting MFA enrollment — the app scans the otpauth URI as a QR code. */
public class MfaSetupResponse {

	private String secret;
	private String otpauthUri;

	public MfaSetupResponse(String secret, String otpauthUri) {
		this.secret = secret;
		this.otpauthUri = otpauthUri;
	}

	public String getSecret() { return secret; }
	public void setSecret(String secret) { this.secret = secret; }
	public String getOtpauthUri() { return otpauthUri; }
	public void setOtpauthUri(String otpauthUri) { this.otpauthUri = otpauthUri; }
}
