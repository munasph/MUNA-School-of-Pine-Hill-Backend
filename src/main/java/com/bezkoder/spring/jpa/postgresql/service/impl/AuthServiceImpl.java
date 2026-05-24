package com.bezkoder.spring.jpa.postgresql.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bezkoder.spring.jpa.postgresql.dto.auth.AuthResponse;
import com.bezkoder.spring.jpa.postgresql.dto.auth.LoginRequest;
import com.bezkoder.spring.jpa.postgresql.dto.auth.SignupRequest;
import com.bezkoder.spring.jpa.postgresql.exception.BadRequestException;
import com.bezkoder.spring.jpa.postgresql.exception.UnauthorizedException;
import com.bezkoder.spring.jpa.postgresql.service.AuthService;

/**
 * Dev-only auth stub. Replace with JWT + Spring Security + user table in production.
 */
@Service
public class AuthServiceImpl implements AuthService {

	private final String adminEmail;
	private final String adminPassword;

	public AuthServiceImpl(
			@Value("${app.admin.email}") String adminEmail,
			@Value("${app.admin.password}") String adminPassword) {
		this.adminEmail = adminEmail;
		this.adminPassword = adminPassword;
	}

	@Override
	public AuthResponse login(LoginRequest request) {
		if (!adminEmail.equalsIgnoreCase(request.getEmail())
				|| !adminPassword.equals(request.getPassword())) {
			throw new UnauthorizedException("Invalid email or password.");
		}

		AuthResponse response = new AuthResponse(true, "Login successful.");
		response.setToken("dev-token-" + UUID.randomUUID());
		response.setEmail(adminEmail);
		response.setRoles(List.of("ADMIN"));
		return response;
	}

	@Override
	public AuthResponse signup(SignupRequest request) {
		if (!request.getPassword().equals(request.getConfirmPassword())) {
			throw new BadRequestException("Passwords do not match.");
		}

		// Public signup is disabled until a user-management flow exists.
		throw new BadRequestException(
				"Public signup is not enabled. Contact an administrator for access.");
	}
}
