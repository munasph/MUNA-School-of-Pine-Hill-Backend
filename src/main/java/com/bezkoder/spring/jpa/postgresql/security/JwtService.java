package com.bezkoder.spring.jpa.postgresql.security;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

	/** Distinguishes admin console tokens from family-portal tokens. */
	public static final String TYPE_ADMIN = "ADMIN";
	public static final String TYPE_PORTAL = "PORTAL";

	private final SecretKey signingKey;
	private final long adminExpirationMs;
	private final long portalExpirationMs;

	public JwtService(
			@Value("${app.jwt.secret}") String secret,
			@Value("${app.jwt.expiration-ms:28800000}") long adminExpirationMs,
			@Value("${app.jwt.portal-expiration-ms:604800000}") long portalExpirationMs) {
		this.signingKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
		this.adminExpirationMs = adminExpirationMs;
		this.portalExpirationMs = portalExpirationMs;
	}

	/** Admin console token (short-lived). */
	public String generateToken(String email, List<String> roles) {
		return generateToken(email, roles, TYPE_ADMIN, adminExpirationMs);
	}

	/** Family-portal token (longer-lived). */
	public String generatePortalToken(String email, List<String> roles) {
		return generateToken(email, roles, TYPE_PORTAL, portalExpirationMs);
	}

	public String generateToken(String email, List<String> roles, String type, long ttlMs) {
		Date now = new Date();
		Date expiry = new Date(now.getTime() + ttlMs);

		return Jwts.builder()
				.subject(email)
				.claim("roles", roles)
				.claim("type", type)
				.issuedAt(now)
				.expiration(expiry)
				.signWith(signingKey)
				.compact();
	}

	public Claims parseClaims(String token) {
		return Jwts.parser()
				.verifyWith(signingKey)
				.build()
				.parseSignedClaims(token)
				.getPayload();
	}

	public String getType(String token) {
		try {
			return parseClaims(token).get("type", String.class);
		} catch (Exception ex) {
			return null;
		}
	}

	public boolean isTokenValid(String token) {
		try {
			Claims claims = parseClaims(token);
			return claims.getExpiration().after(new Date());
		} catch (Exception ex) {
			return false;
		}
	}
}
