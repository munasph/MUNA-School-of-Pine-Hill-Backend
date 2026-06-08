package com.bezkoder.spring.jpa.postgresql.security;

import com.bezkoder.spring.jpa.postgresql.exception.BadRequestException;

public final class PasswordPolicy {

	private PasswordPolicy() {
	}

	public static void validate(String password) {
		if (password == null || password.length() < 8) {
			throw new BadRequestException("Password must be at least 8 characters.");
		}
	}
}
