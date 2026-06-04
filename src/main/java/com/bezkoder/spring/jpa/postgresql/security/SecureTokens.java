package com.bezkoder.spring.jpa.postgresql.security;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.HexFormat;

/**
 * Generates cryptographically-random, URL-safe tokens (for email verification and
 * password resets) and hashes them for at-rest storage. We only ever store the
 * SHA-256 hash — the raw token lives solely in the email link.
 */
public final class SecureTokens {

	private static final SecureRandom RANDOM = new SecureRandom();
	private static final Base64.Encoder URL_ENCODER = Base64.getUrlEncoder().withoutPadding();

	private SecureTokens() {
	}

	/** Returns a 256-bit random, URL-safe token. */
	public static String randomToken() {
		byte[] bytes = new byte[32];
		RANDOM.nextBytes(bytes);
		return URL_ENCODER.encodeToString(bytes);
	}

	/** SHA-256 hex digest of the given raw token, used as the stored lookup key. */
	public static String hash(String rawToken) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hashed = digest.digest(rawToken.getBytes(StandardCharsets.UTF_8));
			return HexFormat.of().formatHex(hashed);
		} catch (NoSuchAlgorithmException ex) {
			throw new IllegalStateException("SHA-256 not available", ex);
		}
	}
}
