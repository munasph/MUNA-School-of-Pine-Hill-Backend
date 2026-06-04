package com.bezkoder.spring.jpa.postgresql.service.impl;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bezkoder.spring.jpa.postgresql.dto.auth.AuthResponse;
import com.bezkoder.spring.jpa.postgresql.dto.auth.LoginRequest;
import com.bezkoder.spring.jpa.postgresql.dto.auth.SignupRequest;
import com.bezkoder.spring.jpa.postgresql.entity.AdminUser;
import com.bezkoder.spring.jpa.postgresql.exception.BadRequestException;
import com.bezkoder.spring.jpa.postgresql.exception.UnauthorizedException;
import com.bezkoder.spring.jpa.postgresql.repository.AdminUserRepository;
import com.bezkoder.spring.jpa.postgresql.security.JwtService;
import com.bezkoder.spring.jpa.postgresql.security.TotpService;
import com.bezkoder.spring.jpa.postgresql.service.AuthAuditService;
import com.bezkoder.spring.jpa.postgresql.service.AuthService;
import com.bezkoder.spring.jpa.postgresql.service.LoginRateLimiter;

@Service
public class AuthServiceImpl implements AuthService {

	private final AdminUserRepository adminUserRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	private final TotpService totpService;
	private final AuthAuditService auditService;
	private final LoginRateLimiter rateLimiter;

	private final int maxFailedLogins;
	private final int lockoutMinutes;

	public AuthServiceImpl(
			AdminUserRepository adminUserRepository,
			PasswordEncoder passwordEncoder,
			JwtService jwtService,
			TotpService totpService,
			AuthAuditService auditService,
			LoginRateLimiter rateLimiter,
			@Value("${app.security.max-failed-logins:5}") int maxFailedLogins,
			@Value("${app.security.lockout-minutes:15}") int lockoutMinutes) {
		this.adminUserRepository = adminUserRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtService = jwtService;
		this.totpService = totpService;
		this.auditService = auditService;
		this.rateLimiter = rateLimiter;
		this.maxFailedLogins = maxFailedLogins;
		this.lockoutMinutes = lockoutMinutes;
	}

	@Override
	@Transactional
	public AuthResponse login(LoginRequest request, String clientIp) {
		String email = request.getEmail().trim().toLowerCase();
		rateLimiter.checkAndIncrement("admin-login:" + clientIp + ":" + email);

		AdminUser admin = adminUserRepository.findByEmailIgnoreCase(email)
				.orElseThrow(() -> new UnauthorizedException("Invalid email or password."));

		if (!admin.isActive()) {
			throw new UnauthorizedException("This account has been disabled.");
		}
		if (isLockedOut(admin)) {
			throw new UnauthorizedException("Account temporarily locked due to failed attempts. Try again later.");
		}

		if (!passwordEncoder.matches(request.getPassword(), admin.getPasswordHash())) {
			registerFailedLogin(admin);
			auditService.record("ADMIN_LOGIN_FAIL", email, "Bad password from " + clientIp);
			throw new UnauthorizedException("Invalid email or password.");
		}

		if (admin.isMfaEnabled()) {
			if (request.getMfaCode() == null || request.getMfaCode().isBlank()) {
				return AuthResponse.mfaChallenge();
			}
			if (!totpService.verifyCode(admin.getMfaSecret(), request.getMfaCode())) {
				registerFailedLogin(admin);
				auditService.record("ADMIN_LOGIN_FAIL", email, "Bad MFA code from " + clientIp);
				throw new UnauthorizedException("Invalid authentication code.");
			}
		}

		clearLockout(admin);
		admin.setLastLoginAt(Instant.now());
		adminUserRepository.save(admin);
		rateLimiter.reset("admin-login:" + clientIp + ":" + email);
		auditService.record("ADMIN_LOGIN", email, "Successful login from " + clientIp);

		List<String> roles = List.of(admin.getRole().name());
		AuthResponse response = new AuthResponse(true, "Login successful.");
		response.setToken(jwtService.generateToken(admin.getEmail(), roles));
		response.setEmail(admin.getEmail());
		response.setRoles(roles);
		return response;
	}

	@Override
	public AuthResponse signup(SignupRequest request) {
		if (!request.getPassword().equals(request.getConfirmPassword())) {
			throw new BadRequestException("Passwords do not match.");
		}
		throw new BadRequestException(
				"Admin signup is not enabled. Contact a system administrator for access.");
	}

	private boolean isLockedOut(AdminUser admin) {
		return admin.getLockoutUntil() != null && admin.getLockoutUntil().isAfter(Instant.now());
	}

	private void registerFailedLogin(AdminUser admin) {
		int attempts = admin.getFailedLoginAttempts() + 1;
		admin.setFailedLoginAttempts(attempts);
		if (attempts >= maxFailedLogins) {
			admin.setLockoutUntil(Instant.now().plus(lockoutMinutes, ChronoUnit.MINUTES));
			admin.setFailedLoginAttempts(0);
		}
		adminUserRepository.save(admin);
	}

	private void clearLockout(AdminUser admin) {
		admin.setFailedLoginAttempts(0);
		admin.setLockoutUntil(null);
	}
}
