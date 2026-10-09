package com.bezkoder.spring.jpa.postgresql.security;

import java.util.regex.Pattern;

import com.bezkoder.spring.jpa.postgresql.exception.BadRequestException;

public final class UsernamePolicy {

	private static final Pattern USERNAME_PATTERN = Pattern.compile("^[a-zA-Z0-9._-]{3,50}$");

	private UsernamePolicy() {
	}

	public static String normalize(String raw) {
		if (raw == null || raw.isBlank()) {
			throw new BadRequestException("Username is required.");
		}
		String username = raw.trim().toLowerCase();
		if (!USERNAME_PATTERN.matcher(username).matches()) {
			throw new BadRequestException(
					"Username must be 3–50 characters and use only letters, numbers, dots, underscores, or hyphens.");
		}
		return username;
	}
}
