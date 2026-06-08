package com.bezkoder.spring.jpa.postgresql.service.impl;

import java.time.Instant;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bezkoder.spring.jpa.postgresql.dto.auth.AuthResponse;
import com.bezkoder.spring.jpa.postgresql.dto.auth.LoginRequest;
import com.bezkoder.spring.jpa.postgresql.dto.auth.SignupRequest;
import com.bezkoder.spring.jpa.postgresql.entity.AdminUser;
import com.bezkoder.spring.jpa.postgresql.entity.enums.AdminAccountStatus;
import com.bezkoder.spring.jpa.postgresql.entity.enums.AdminApprovalStatus;
import com.bezkoder.spring.jpa.postgresql.exception.BadRequestException;
import com.bezkoder.spring.jpa.postgresql.exception.UnauthorizedException;
import com.bezkoder.spring.jpa.postgresql.repository.AdminUserRepository;
import com.bezkoder.spring.jpa.postgresql.security.LoginRateLimiter;
import com.bezkoder.spring.jpa.postgresql.security.JwtService;
import com.bezkoder.spring.jpa.postgresql.service.AuthAuditService;
import com.bezkoder.spring.jpa.postgresql.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {

	private static final int MAX_FAILED_ATTEMPTS = 5;
	private static final long LOCKOUT_MINUTES = 15;

	private final AdminUserRepository adminUserRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	private final LoginRateLimiter loginRateLimiter;
	private final AuthAuditService authAuditService;

	public AuthServiceImpl(
			AdminUserRepository adminUserRepository,
			PasswordEncoder passwordEncoder,
			JwtService jwtService,
			LoginRateLimiter loginRateLimiter,
			AuthAuditService authAuditService) {
		this.adminUserRepository = adminUserRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtService = jwtService;
		this.loginRateLimiter = loginRateLimiter;
		this.authAuditService = authAuditService;
	}

	@Override
	@Transactional
	public AuthResponse login(LoginRequest request) {
		String email = request.getEmail().trim().toLowerCase();
		try {
			loginRateLimiter.assertAllowed(email);
		} catch (IllegalStateException ex) {
			throw new UnauthorizedException(ex.getMessage());
		}

		AdminUser admin = adminUserRepository.findByEmailIgnoreCase(email)
				.orElseThrow(() -> {
					authAuditService.log("LOGIN_FAILED", "admin_user", email, email, "Unknown email");
					return new UnauthorizedException("Invalid email or password.");
				});

		if (admin.getLockoutUntil() != null && admin.getLockoutUntil().isAfter(Instant.now())) {
			throw new UnauthorizedException("Account temporarily locked. Try again later.");
		}

		if (!admin.isActive() || admin.getAccountStatus() == AdminAccountStatus.DISABLED) {
			throw new UnauthorizedException("This account is disabled.");
		}

		if (admin.getApprovalStatus() == AdminApprovalStatus.PENDING) {
			throw new UnauthorizedException("Your account is pending approval.");
		}

		if (admin.getApprovalStatus() == AdminApprovalStatus.REJECTED) {
			throw new UnauthorizedException("Your account request was not approved.");
		}

		if (!passwordEncoder.matches(request.getPassword(), admin.getPasswordHash())) {
			registerFailedAttempt(admin);
			authAuditService.log("LOGIN_FAILED", "admin_user", admin.getId().toString(), email, "Bad password");
			throw new UnauthorizedException("Invalid email or password.");
		}

		admin.setFailedLoginAttempts(0);
		admin.setLockoutUntil(null);
		admin.setLastLoginAt(Instant.now());
		adminUserRepository.save(admin);
		loginRateLimiter.reset(email);

		List<String> roles = List.of(admin.getRole().name());
		AuthResponse response = new AuthResponse(true, "Login successful.");
		response.setToken(jwtService.generateToken(admin.getEmail(), roles));
		response.setEmail(admin.getEmail());
		response.setRoles(roles);
		authAuditService.log("LOGIN_SUCCESS", "admin_user", admin.getId().toString(), email, "Admin login");
		return response;
	}

	@Override
	public AuthResponse signup(SignupRequest request) {
		if (!request.getPassword().equals(request.getConfirmPassword())) {
			throw new BadRequestException("Passwords do not match.");
		}
		throw new BadRequestException("Use the staff signup page to request access.");
	}

	private void registerFailedAttempt(AdminUser admin) {
		int attempts = admin.getFailedLoginAttempts() + 1;
		admin.setFailedLoginAttempts(attempts);
		if (attempts >= MAX_FAILED_ATTEMPTS) {
			admin.setLockoutUntil(Instant.now().plusSeconds(LOCKOUT_MINUTES * 60));
		}
		adminUserRepository.save(admin);
	}
}
