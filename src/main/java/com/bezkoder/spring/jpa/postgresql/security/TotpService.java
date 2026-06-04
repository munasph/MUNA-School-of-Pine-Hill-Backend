package com.bezkoder.spring.jpa.postgresql.security;

import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.time.Instant;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Service;

/**
 * RFC 6238 TOTP (time-based one-time passwords) compatible with Google Authenticator,
 * Authy, 1Password, etc. Implemented with the JDK only — no external dependency.
 */
@Service
public class TotpService {

	private static final int TIME_STEP_SECONDS = 30;
	private static final int DIGITS = 6;
	private static final int ALLOWED_DRIFT_STEPS = 1;
	private static final String HMAC_ALGO = "HmacSHA1";
	private static final char[] BASE32 = "ABCDEFGHIJKLMNOPQRSTUVWXYZ234567".toCharArray();

	private final SecureRandom random = new SecureRandom();

	/** Generates a new Base32 secret (160 bits) to store against the user. */
	public String generateSecret() {
		byte[] bytes = new byte[20];
		random.nextBytes(bytes);
		return base32Encode(bytes);
	}

	/** Builds an otpauth:// URI that authenticator apps render as a QR code. */
	public String buildOtpAuthUri(String issuer, String accountEmail, String secret) {
		String label = urlEncode(issuer) + ":" + urlEncode(accountEmail);
		return "otpauth://totp/" + label
				+ "?secret=" + secret
				+ "&issuer=" + urlEncode(issuer)
				+ "&algorithm=SHA1&digits=" + DIGITS + "&period=" + TIME_STEP_SECONDS;
	}

	/** Validates a user-supplied 6-digit code, tolerating ±1 time step of clock drift. */
	public boolean verifyCode(String secret, String code) {
		if (secret == null || code == null) {
			return false;
		}
		String trimmed = code.trim();
		if (!trimmed.matches("\\d{6}")) {
			return false;
		}

		byte[] key = base32Decode(secret);
		long currentStep = Instant.now().getEpochSecond() / TIME_STEP_SECONDS;
		for (long offset = -ALLOWED_DRIFT_STEPS; offset <= ALLOWED_DRIFT_STEPS; offset++) {
			if (generateCode(key, currentStep + offset).equals(trimmed)) {
				return true;
			}
		}
		return false;
	}

	private String generateCode(byte[] key, long timeStep) {
		try {
			byte[] data = ByteBuffer.allocate(8).putLong(timeStep).array();
			Mac mac = Mac.getInstance(HMAC_ALGO);
			mac.init(new SecretKeySpec(key, HMAC_ALGO));
			byte[] hmac = mac.doFinal(data);

			int offset = hmac[hmac.length - 1] & 0x0F;
			int binary = ((hmac[offset] & 0x7F) << 24)
					| ((hmac[offset + 1] & 0xFF) << 16)
					| ((hmac[offset + 2] & 0xFF) << 8)
					| (hmac[offset + 3] & 0xFF);

			int otp = binary % (int) Math.pow(10, DIGITS);
			return String.format("%0" + DIGITS + "d", otp);
		} catch (Exception ex) {
			throw new IllegalStateException("Unable to generate TOTP code", ex);
		}
	}

	private static String base32Encode(byte[] data) {
		StringBuilder result = new StringBuilder();
		int buffer = 0;
		int bitsLeft = 0;
		for (byte b : data) {
			buffer = (buffer << 8) | (b & 0xFF);
			bitsLeft += 8;
			while (bitsLeft >= 5) {
				int index = (buffer >> (bitsLeft - 5)) & 0x1F;
				bitsLeft -= 5;
				result.append(BASE32[index]);
			}
		}
		if (bitsLeft > 0) {
			int index = (buffer << (5 - bitsLeft)) & 0x1F;
			result.append(BASE32[index]);
		}
		return result.toString();
	}

	private static byte[] base32Decode(String encoded) {
		String clean = encoded.trim().replace("=", "").toUpperCase();
		int buffer = 0;
		int bitsLeft = 0;
		ByteBuffer out = ByteBuffer.allocate(clean.length() * 5 / 8);
		for (char c : clean.toCharArray()) {
			int val = indexOfBase32(c);
			if (val < 0) {
				continue;
			}
			buffer = (buffer << 5) | val;
			bitsLeft += 5;
			if (bitsLeft >= 8) {
				out.put((byte) ((buffer >> (bitsLeft - 8)) & 0xFF));
				bitsLeft -= 8;
			}
		}
		byte[] result = new byte[out.position()];
		out.rewind();
		out.get(result);
		return result;
	}

	private static int indexOfBase32(char c) {
		for (int i = 0; i < BASE32.length; i++) {
			if (BASE32[i] == c) {
				return i;
			}
		}
		return -1;
	}

	private static String urlEncode(String value) {
		return java.net.URLEncoder.encode(value, java.nio.charset.StandardCharsets.UTF_8);
	}
}
