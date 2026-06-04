package com.bezkoder.spring.jpa.postgresql.service.impl;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bezkoder.spring.jpa.postgresql.dto.portal.ForgotPasswordRequest;
import com.bezkoder.spring.jpa.postgresql.dto.portal.PortalAuthResponse;
import com.bezkoder.spring.jpa.postgresql.dto.portal.PortalLoginRequest;
import com.bezkoder.spring.jpa.postgresql.dto.portal.PortalSignupRequest;
import com.bezkoder.spring.jpa.postgresql.dto.portal.ResetPasswordRequest;
import com.bezkoder.spring.jpa.postgresql.entity.EmailVerificationToken;
import com.bezkoder.spring.jpa.postgresql.entity.MagicLinkToken;
import com.bezkoder.spring.jpa.postgresql.entity.PasswordResetToken;
import com.bezkoder.spring.jpa.postgresql.entity.PortalUser;
import com.bezkoder.spring.jpa.postgresql.exception.BadRequestException;
import com.bezkoder.spring.jpa.postgresql.exception.UnauthorizedException;
import com.bezkoder.spring.jpa.postgresql.repository.EmailVerificationTokenRepository;
import com.bezkoder.spring.jpa.postgresql.repository.MagicLinkTokenRepository;
import com.bezkoder.spring.jpa.postgresql.repository.PasswordResetTokenRepository;
import com.bezkoder.spring.jpa.postgresql.repository.PortalUserRepository;
import com.bezkoder.spring.jpa.postgresql.security.JwtService;
import com.bezkoder.spring.jpa.postgresql.security.PasswordPolicy;
import com.bezkoder.spring.jpa.postgresql.security.SecureTokens;
import com.bezkoder.spring.jpa.postgresql.security.TotpService;
import com.bezkoder.spring.jpa.postgresql.service.AuthAuditService;
import com.bezkoder.spring.jpa.postgresql.service.EmailService;
import com.bezkoder.spring.jpa.postgresql.service.LoginRateLimiter;
import com.bezkoder.spring.jpa.postgresql.service.PortalAuthService;

@Service
public class PortalAuthServiceImpl implements PortalAuthService {

	private static final String GENERIC_RESET_MESSAGE =
			"If an account exists for that email, a reset link has been sent.";

	private final PortalUserRepository portalUserRepository;
	private final EmailVerificationTokenRepository verificationTokenRepository;
	private final PasswordResetTokenRepository resetTokenRepository;
	private final MagicLinkTokenRepository magicLinkTokenRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	private final TotpService totpService;
	private final EmailService emailService;
	private final AuthAuditService auditService;
	private final LoginRateLimiter rateLimiter;

	private final String frontendBaseUrl;
	private final int maxFailedLogins;
	private final int lockoutMinutes;
	private final int emailTokenTtlMinutes;
	private final int resetTokenTtlMinutes;
	private final int magicLinkTtlMinutes;
	private final boolean magicLinkEnabled;

	public PortalAuthServiceImpl(
			PortalUserRepository portalUserRepository,
			EmailVerificationTokenRepository verificationTokenRepository,
			PasswordResetTokenRepository resetTokenRepository,
			MagicLinkTokenRepository magicLinkTokenRepository,
			PasswordEncoder passwordEncoder,
			JwtService jwtService,
			TotpService totpService,
			EmailService emailService,
			AuthAuditService auditService,
			LoginRateLimiter rateLimiter,
			@Value("${app.frontend.base-url}") String frontendBaseUrl,
			@Value("${app.security.max-failed-logins:5}") int maxFailedLogins,
			@Value("${app.security.lockout-minutes:15}") int lockoutMinutes,
			@Value("${app.security.email-token-ttl-minutes:1440}") int emailTokenTtlMinutes,
			@Value("${app.security.reset-token-ttl-minutes:60}") int resetTokenTtlMinutes,
			@Value("${app.security.magic-link-ttl-minutes:15}") int magicLinkTtlMinutes,
			@Value("${app.auth.magic-link-enabled:true}") boolean magicLinkEnabled) {
		this.portalUserRepository = portalUserRepository;
		this.verificationTokenRepository = verificationTokenRepository;
		this.resetTokenRepository = resetTokenRepository;
		this.magicLinkTokenRepository = magicLinkTokenRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtService = jwtService;
		this.totpService = totpService;
		this.emailService = emailService;
		this.auditService = auditService;
		this.rateLimiter = rateLimiter;
		this.frontendBaseUrl = frontendBaseUrl.replaceAll("/$", "");
		this.maxFailedLogins = maxFailedLogins;
		this.lockoutMinutes = lockoutMinutes;
		this.emailTokenTtlMinutes = emailTokenTtlMinutes;
		this.resetTokenTtlMinutes = resetTokenTtlMinutes;
		this.magicLinkTtlMinutes = magicLinkTtlMinutes;
		this.magicLinkEnabled = magicLinkEnabled;
	}

	@Override
	@Transactional
	public PortalAuthResponse signup(PortalSignupRequest request, String clientIp) {
		rateLimiter.checkAndIncrement("signup:" + clientIp);

		if (!request.getPassword().equals(request.getConfirmPassword())) {
			throw new BadRequestException("Passwords do not match.");
		}
		PasswordPolicy.validate(request.getPassword());

		String email = request.getEmail().trim().toLowerCase();
		if (portalUserRepository.existsByEmailIgnoreCase(email)) {
			// Avoid leaking which emails are registered.
			throw new BadRequestException("Unable to create account. Try logging in or resetting your password.");
		}

		PortalUser user = new PortalUser();
		user.setEmail(email);
		user.setFullName(request.getFullName().trim());
		user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
		user.setRole(request.getRole());
		user.setEmailVerified(false);
		user.setActive(true);
		portalUserRepository.save(user);

		issueVerificationEmail(user);
		auditService.record("PORTAL_SIGNUP", email, "Portal account created (" + request.getRole() + ")");

		return new PortalAuthResponse(true,
				"Account created. Check your email to verify your address before logging in.");
	}

	@Override
	@Transactional
	public PortalAuthResponse login(PortalLoginRequest request, String clientIp) {
		String email = request.getEmail().trim().toLowerCase();
		rateLimiter.checkAndIncrement("login:" + clientIp + ":" + email);

		PortalUser user = portalUserRepository.findByEmailIgnoreCase(email)
				.orElseThrow(() -> new UnauthorizedException("Invalid email or password."));

		if (!user.isActive()) {
			throw new UnauthorizedException("This account has been disabled.");
		}
		if (isLockedOut(user)) {
			throw new UnauthorizedException("Account temporarily locked due to failed attempts. Try again later.");
		}

		if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
			registerFailedLogin(user);
			auditService.record("PORTAL_LOGIN_FAIL", email, "Bad password");
			throw new UnauthorizedException("Invalid email or password.");
		}

		if (!user.isEmailVerified()) {
			throw new UnauthorizedException("Please verify your email before logging in.");
		}

		if (user.isMfaEnabled()) {
			if (request.getMfaCode() == null || request.getMfaCode().isBlank()) {
				return PortalAuthResponse.mfaChallenge();
			}
			if (!totpService.verifyCode(user.getMfaSecret(), request.getMfaCode())) {
				registerFailedLogin(user);
				auditService.record("PORTAL_LOGIN_FAIL", email, "Bad MFA code");
				throw new UnauthorizedException("Invalid authentication code.");
			}
		}

		clearLockout(user);
		user.setLastLoginAt(Instant.now());
		portalUserRepository.save(user);
		rateLimiter.reset("login:" + clientIp + ":" + email);
		auditService.record("PORTAL_LOGIN", email, "Successful login");

		return buildAuthSuccess(user);
	}

	@Override
	@Transactional
	public PortalAuthResponse verifyEmail(String rawToken) {
		EmailVerificationToken token = verificationTokenRepository.findByTokenHash(SecureTokens.hash(rawToken))
				.orElseThrow(() -> new BadRequestException("Invalid or expired verification link."));

		if (!token.isUsable()) {
			throw new BadRequestException("This verification link has expired. Request a new one.");
		}

		PortalUser user = portalUserRepository.findById(token.getUserId())
				.orElseThrow(() -> new BadRequestException("Account no longer exists."));

		user.setEmailVerified(true);
		portalUserRepository.save(user);

		token.setUsedAt(Instant.now());
		verificationTokenRepository.save(token);
		auditService.record("PORTAL_EMAIL_VERIFIED", user.getEmail(), "Email verified");

		return new PortalAuthResponse(true, "Email verified. You can now log in.");
	}

	@Override
	@Transactional
	public PortalAuthResponse resendVerification(ForgotPasswordRequest request) {
		String email = request.getEmail().trim().toLowerCase();
		portalUserRepository.findByEmailIgnoreCase(email).ifPresent(user -> {
			if (!user.isEmailVerified()) {
				issueVerificationEmail(user);
			}
		});
		return new PortalAuthResponse(true,
				"If that account exists and is unverified, a new verification link has been sent.");
	}

	@Override
	@Transactional
	public PortalAuthResponse forgotPassword(ForgotPasswordRequest request, String clientIp) {
		rateLimiter.checkAndIncrement("forgot:" + clientIp);
		String email = request.getEmail().trim().toLowerCase();

		Optional<PortalUser> maybeUser = portalUserRepository.findByEmailIgnoreCase(email);
		if (maybeUser.isPresent()) {
			PortalUser user = maybeUser.get();
			resetTokenRepository.deleteByUserId(user.getId());

			String raw = SecureTokens.randomToken();
			PasswordResetToken token = new PasswordResetToken();
			token.setUserId(user.getId());
			token.setTokenHash(SecureTokens.hash(raw));
			token.setExpiresAt(Instant.now().plus(resetTokenTtlMinutes, ChronoUnit.MINUTES));
			resetTokenRepository.save(token);

			String link = frontendBaseUrl + "/portal/reset-password?token=" + raw;
			emailService.sendPasswordResetEmail(user.getEmail(), user.getFullName(), link);
			auditService.record("PORTAL_RESET_REQUEST", email, "Password reset requested");
		}

		return new PortalAuthResponse(true, GENERIC_RESET_MESSAGE);
	}

	@Override
	@Transactional
	public PortalAuthResponse resetPassword(ResetPasswordRequest request) {
		if (!request.getPassword().equals(request.getConfirmPassword())) {
			throw new BadRequestException("Passwords do not match.");
		}
		PasswordPolicy.validate(request.getPassword());

		PasswordResetToken token = resetTokenRepository.findByTokenHash(SecureTokens.hash(request.getToken()))
				.orElseThrow(() -> new BadRequestException("Invalid or expired reset link."));

		if (!token.isUsable()) {
			throw new BadRequestException("This reset link has expired. Request a new one.");
		}

		PortalUser user = portalUserRepository.findById(token.getUserId())
				.orElseThrow(() -> new BadRequestException("Account no longer exists."));

		user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
		clearLockout(user);
		portalUserRepository.save(user);

		token.setUsedAt(Instant.now());
		resetTokenRepository.save(token);
		auditService.record("PORTAL_RESET_DONE", user.getEmail(), "Password reset completed");

		return new PortalAuthResponse(true, "Password updated. You can now log in.");
	}

	@Override
	@Transactional
	public PortalAuthResponse requestMagicLink(String email, String clientIp) {
		if (!magicLinkEnabled) {
			throw new BadRequestException("Magic-link login is not available.");
		}
		rateLimiter.checkAndIncrement("magic:" + clientIp);
		String normalized = email.trim().toLowerCase();

		portalUserRepository.findByEmailIgnoreCase(normalized).ifPresent(user -> {
			if (!user.isActive()) {
				return;
			}
			magicLinkTokenRepository.deleteByUserId(user.getId());

			String raw = SecureTokens.randomToken();
			MagicLinkToken token = new MagicLinkToken();
			token.setUserId(user.getId());
			token.setTokenHash(SecureTokens.hash(raw));
			token.setExpiresAt(Instant.now().plus(magicLinkTtlMinutes, ChronoUnit.MINUTES));
			magicLinkTokenRepository.save(token);

			String link = frontendBaseUrl + "/portal/magic-link?token=" + raw;
			emailService.sendMagicLinkEmail(user.getEmail(), user.getFullName(), link);
			auditService.record("PORTAL_MAGIC_REQUEST", normalized, "Magic link requested");
		});

		return new PortalAuthResponse(true,
				"If an account exists for that email, a login link has been sent.");
	}

	@Override
	@Transactional
	public PortalAuthResponse consumeMagicLink(String rawToken) {
		MagicLinkToken token = magicLinkTokenRepository.findByTokenHash(SecureTokens.hash(rawToken))
				.orElseThrow(() -> new BadRequestException("Invalid or expired login link."));

		if (!token.isUsable()) {
			throw new BadRequestException("This login link has expired. Request a new one.");
		}

		PortalUser user = portalUserRepository.findById(token.getUserId())
				.orElseThrow(() -> new BadRequestException("Account no longer exists."));

		if (!user.isActive()) {
			throw new UnauthorizedException("This account has been disabled.");
		}

		token.setUsedAt(Instant.now());
		magicLinkTokenRepository.save(token);

		// Following a magic link proves email ownership, so verify the address too.
		if (!user.isEmailVerified()) {
			user.setEmailVerified(true);
		}
		clearLockout(user);
		user.setLastLoginAt(Instant.now());
		portalUserRepository.save(user);
		auditService.record("PORTAL_MAGIC_LOGIN", user.getEmail(), "Magic link login");

		return buildAuthSuccess(user);
	}

	private void issueVerificationEmail(PortalUser user) {
		verificationTokenRepository.deleteByUserId(user.getId());

		String raw = SecureTokens.randomToken();
		EmailVerificationToken token = new EmailVerificationToken();
		token.setUserId(user.getId());
		token.setTokenHash(SecureTokens.hash(raw));
		token.setExpiresAt(Instant.now().plus(emailTokenTtlMinutes, ChronoUnit.MINUTES));
		verificationTokenRepository.save(token);

		String link = frontendBaseUrl + "/portal/verify-email?token=" + raw;
		emailService.sendVerificationEmail(user.getEmail(), user.getFullName(), link);
	}

	private boolean isLockedOut(PortalUser user) {
		return user.getLockoutUntil() != null && user.getLockoutUntil().isAfter(Instant.now());
	}

	private void registerFailedLogin(PortalUser user) {
		int attempts = user.getFailedLoginAttempts() + 1;
		user.setFailedLoginAttempts(attempts);
		if (attempts >= maxFailedLogins) {
			user.setLockoutUntil(Instant.now().plus(lockoutMinutes, ChronoUnit.MINUTES));
			user.setFailedLoginAttempts(0);
		}
		portalUserRepository.save(user);
	}

	private void clearLockout(PortalUser user) {
		user.setFailedLoginAttempts(0);
		user.setLockoutUntil(null);
	}

	private PortalAuthResponse buildAuthSuccess(PortalUser user) {
		List<String> roles = List.of(user.getRole().name());
		PortalAuthResponse response = new PortalAuthResponse(true, "Login successful.");
		response.setToken(jwtService.generatePortalToken(user.getEmail(), roles));
		response.setEmail(user.getEmail());
		response.setFullName(user.getFullName());
		response.setRole(user.getRole().name());
		return response;
	}
}
