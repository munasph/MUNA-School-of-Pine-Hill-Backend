package com.bezkoder.spring.jpa.postgresql.dto.portal;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class PortalLoginRequest {

	@NotBlank
	@Email
	private String email;

	@NotBlank
	@Size(min = 8, max = 100)
	private String password;

	/** Optional TOTP code, required only when the account has MFA enabled. */
	private String mfaCode;

	public String getEmail() { return email; }
	public void setEmail(String email) { this.email = email; }
	public String getPassword() { return password; }
	public void setPassword(String password) { this.password = password; }
	public String getMfaCode() { return mfaCode; }
	public void setMfaCode(String mfaCode) { this.mfaCode = mfaCode; }
}
