package com.bezkoder.spring.jpa.postgresql.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bezkoder.spring.jpa.postgresql.dto.auth.AuthResponse;
import com.bezkoder.spring.jpa.postgresql.dto.auth.LoginRequest;
import com.bezkoder.spring.jpa.postgresql.dto.auth.SignupRequest;
import com.bezkoder.spring.jpa.postgresql.exception.BadRequestException;
import com.bezkoder.spring.jpa.postgresql.exception.UnauthorizedException;
import com.bezkoder.spring.jpa.postgresql.security.JwtService;
import com.bezkoder.spring.jpa.postgresql.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {

	private final String adminEmail;
	private final String adminPassword;
	private final JwtService jwtService;

	public AuthServiceImpl(
			@Value("${app.admin.email}") String adminEmail,
			@Value("${app.admin.password}") String adminPassword,
			JwtService jwtService) {
		this.adminEmail = adminEmail;
		this.adminPassword = adminPassword;
		this.jwtService = jwtService;
	}

	@Override
	public AuthResponse login(LoginRequest request) {
		if (!adminEmail.equalsIgnoreCase(request.getEmail())
				|| !adminPassword.equals(request.getPassword())) {
			throw new UnauthorizedException("Invalid email or password.");
		}

		List<String> roles = List.of("ADMIN");
		AuthResponse response = new AuthResponse(true, "Login successful.");
		response.setToken(jwtService.generateToken(adminEmail, roles));
		response.setEmail(adminEmail);
		response.setRoles(roles);
		return response;
	}

	@Override
	public AuthResponse signup(SignupRequest request) {
		if (!request.getPassword().equals(request.getConfirmPassword())) {
			throw new BadRequestException("Passwords do not match.");
		}

		throw new BadRequestException(
				"Public signup is not enabled. Contact an administrator for access.");
	}
}
