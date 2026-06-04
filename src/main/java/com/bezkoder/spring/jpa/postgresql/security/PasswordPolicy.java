package com.bezkoder.spring.jpa.postgresql.security;

import com.bezkoder.spring.jpa.postgresql.exception.BadRequestException;

/**
 * Minimum password strength rules for portal accounts. Kept intentionally simple
 * but enforces length + character variety to resist trivial brute force.
 */
public final class PasswordPolicy {

	private static final int MIN_LENGTH = 8;
	private static final int MAX_LENGTH = 100;

	private PasswordPolicy() {
	}

	public static void validate(String password) {
		if (password == null || password.length() < MIN_LENGTH || password.length() > MAX_LENGTH) {
			throw new BadRequestException("Password must be between 8 and 100 characters.");
		}

		boolean hasLetter = password.chars().anyMatch(Character::isLetter);
		boolean hasDigit = password.chars().anyMatch(Character::isDigit);

		if (!hasLetter || !hasDigit) {
			throw new BadRequestException("Password must contain at least one letter and one number.");
		}
	}
}
