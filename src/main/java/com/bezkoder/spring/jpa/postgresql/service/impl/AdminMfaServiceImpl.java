package com.bezkoder.spring.jpa.postgresql.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bezkoder.spring.jpa.postgresql.dto.auth.MfaSetupResponse;
import com.bezkoder.spring.jpa.postgresql.entity.AdminUser;
import com.bezkoder.spring.jpa.postgresql.exception.BadRequestException;
import com.bezkoder.spring.jpa.postgresql.exception.UnauthorizedException;
import com.bezkoder.spring.jpa.postgresql.repository.AdminUserRepository;
import com.bezkoder.spring.jpa.postgresql.security.TotpService;
import com.bezkoder.spring.jpa.postgresql.service.AdminMfaService;
import com.bezkoder.spring.jpa.postgresql.service.AuthAuditService;

@Service
public class AdminMfaServiceImpl implements AdminMfaService {

	private static final String ISSUER = "MUNA School Admin";

	private final AdminUserRepository adminUserRepository;
	private final TotpService totpService;
	private final AuthAuditService auditService;

	public AdminMfaServiceImpl(
			AdminUserRepository adminUserRepository,
			TotpService totpService,
			AuthAuditService auditService) {
		this.adminUserRepository = adminUserRepository;
		this.totpService = totpService;
		this.auditService = auditService;
	}

	@Override
	@Transactional
	public MfaSetupResponse beginEnrollment(String adminEmail) {
		AdminUser admin = requireAdmin(adminEmail);
		String secret = totpService.generateSecret();
		// Store the candidate secret but keep MFA disabled until a valid code confirms it.
		admin.setMfaSecret(secret);
		admin.setMfaEnabled(false);
		adminUserRepository.save(admin);

		String uri = totpService.buildOtpAuthUri(ISSUER, admin.getEmail(), secret);
		return new MfaSetupResponse(secret, uri);
	}

	@Override
	@Transactional
	public void enable(String adminEmail, String code) {
		AdminUser admin = requireAdmin(adminEmail);
		if (admin.getMfaSecret() == null) {
			throw new BadRequestException("Start MFA setup before enabling.");
		}
		if (!totpService.verifyCode(admin.getMfaSecret(), code)) {
			throw new BadRequestException("Invalid authentication code. Try again.");
		}
		admin.setMfaEnabled(true);
		adminUserRepository.save(admin);
		auditService.record("ADMIN_MFA_ENABLED", admin.getEmail(), "MFA enabled");
	}

	@Override
	@Transactional
	public void disable(String adminEmail, String code) {
		AdminUser admin = requireAdmin(adminEmail);
		if (admin.isMfaEnabled() && !totpService.verifyCode(admin.getMfaSecret(), code)) {
			throw new BadRequestException("Invalid authentication code.");
		}
		admin.setMfaEnabled(false);
		admin.setMfaSecret(null);
		adminUserRepository.save(admin);
		auditService.record("ADMIN_MFA_DISABLED", admin.getEmail(), "MFA disabled");
	}

	private AdminUser requireAdmin(String email) {
		return adminUserRepository.findByEmailIgnoreCase(email)
				.orElseThrow(() -> new UnauthorizedException("Admin account not found."));
	}
}
