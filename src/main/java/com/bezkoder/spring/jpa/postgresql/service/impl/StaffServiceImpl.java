package com.bezkoder.spring.jpa.postgresql.service.impl;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bezkoder.spring.jpa.postgresql.dto.auth.PasswordResetConfirmRequest;
import com.bezkoder.spring.jpa.postgresql.dto.auth.PasswordResetRequest;
import com.bezkoder.spring.jpa.postgresql.dto.auth.SetPasswordRequest;
import com.bezkoder.spring.jpa.postgresql.dto.auth.StaffSignupRequest;
import com.bezkoder.spring.jpa.postgresql.dto.staff.StaffInviteRequest;
import com.bezkoder.spring.jpa.postgresql.dto.staff.StaffMemberResponse;
import com.bezkoder.spring.jpa.postgresql.entity.AdminUser;
import com.bezkoder.spring.jpa.postgresql.entity.PasswordResetToken;
import com.bezkoder.spring.jpa.postgresql.entity.enums.AdminAccountStatus;
import com.bezkoder.spring.jpa.postgresql.entity.enums.AdminApprovalStatus;
import com.bezkoder.spring.jpa.postgresql.entity.enums.AdminUserRole;
import com.bezkoder.spring.jpa.postgresql.exception.BadRequestException;
import com.bezkoder.spring.jpa.postgresql.exception.ResourceNotFoundException;
import com.bezkoder.spring.jpa.postgresql.exception.UnauthorizedException;
import com.bezkoder.spring.jpa.postgresql.repository.AdminUserRepository;
import com.bezkoder.spring.jpa.postgresql.repository.PasswordResetTokenRepository;
import com.bezkoder.spring.jpa.postgresql.security.PasswordPolicy;
import com.bezkoder.spring.jpa.postgresql.security.SecureTokens;
import com.bezkoder.spring.jpa.postgresql.service.AuthAuditService;
import com.bezkoder.spring.jpa.postgresql.service.EmailService;
import com.bezkoder.spring.jpa.postgresql.service.StaffService;

@Service
@Transactional(readOnly = true)
public class StaffServiceImpl implements StaffService {

	private static final String PLACEHOLDER_PASSWORD = "{invite-pending}";

	private final AdminUserRepository adminUserRepository;
	private final PasswordResetTokenRepository passwordResetTokenRepository;
	private final PasswordEncoder passwordEncoder;
	private final EmailService emailService;
	private final AuthAuditService authAuditService;
	private final String frontendUrl;

	public StaffServiceImpl(
			AdminUserRepository adminUserRepository,
			PasswordResetTokenRepository passwordResetTokenRepository,
			PasswordEncoder passwordEncoder,
			EmailService emailService,
			AuthAuditService authAuditService,
			@Value("${app.frontend.url:http://localhost:4200}") String frontendUrl) {
		this.adminUserRepository = adminUserRepository;
		this.passwordResetTokenRepository = passwordResetTokenRepository;
		this.passwordEncoder = passwordEncoder;
		this.emailService = emailService;
		this.authAuditService = authAuditService;
		this.frontendUrl = frontendUrl.replaceAll("/$", "");
	}

	@Override
	public List<StaffMemberResponse> listActiveStaff() {
		return adminUserRepository.findAllByOrderByEmailAsc().stream()
				.filter(user -> user.getApprovalStatus() == AdminApprovalStatus.APPROVED)
				.map(this::toResponse)
				.toList();
	}

	@Override
	public List<StaffMemberResponse> listPendingStaff() {
		return adminUserRepository.findAllByApprovalStatusOrderByCreatedAtDesc(AdminApprovalStatus.PENDING)
				.stream()
				.map(this::toResponse)
				.toList();
	}

	@Override
	@Transactional
	public StaffMemberResponse inviteStaff(StaffInviteRequest request, String actorEmail) {
		assertSuperAdminActor(actorEmail);
		validateStaffRole(request.getRole());

		String email = normalizeEmail(request.getEmail());
		if (adminUserRepository.existsByEmailIgnoreCase(email)) {
			throw new BadRequestException("A user with this email already exists.");
		}

		String rawToken = SecureTokens.generateRawToken();
		AdminUser inviter = findByEmailOrThrow(actorEmail);
		AdminUser user = new AdminUser();
		user.setEmail(email);
		user.setDisplayName(request.getDisplayName().trim());
		user.setRole(request.getRole());
		user.setActive(true);
		user.setApprovalStatus(AdminApprovalStatus.PENDING);
		user.setAccountStatus(AdminAccountStatus.ACTIVE);
		user.setInvitedBy(inviter.getId());
		user.setPasswordHash(passwordEncoder.encode(PLACEHOLDER_PASSWORD));
		user.setInviteTokenHash(SecureTokens.hashToken(rawToken));
		user.setInviteExpiresAt(Instant.now().plus(48, ChronoUnit.HOURS));
		AdminUser saved = adminUserRepository.save(user);

		emailService.sendStaffInvite(email, frontendUrl + "/set-password?token=" + rawToken);
		authAuditService.log("STAFF_INVITED", "admin_user", saved.getId().toString(), actorEmail,
				"Invited " + email + " as " + request.getRole());

		return toResponse(saved);
	}

	@Override
	@Transactional
	public StaffMemberResponse approveStaff(Long id, String actorEmail) {
		assertSuperAdminActor(actorEmail);
		AdminUser user = findUserOrThrow(id);
		if (user.getApprovalStatus() != AdminApprovalStatus.PENDING) {
			throw new BadRequestException("Only pending staff requests can be approved.");
		}
		if (user.getRole() == AdminUserRole.SUPER_ADMIN) {
			throw new BadRequestException("Cannot approve super admin accounts through this flow.");
		}

		AdminUser approver = findByEmailOrThrow(actorEmail);
		user.setApprovalStatus(AdminApprovalStatus.APPROVED);
		user.setApprovedBy(approver.getId());
		user.setApprovedAt(Instant.now());
		user.setInviteTokenHash(null);
		user.setInviteExpiresAt(null);
		AdminUser saved = adminUserRepository.save(user);

		emailService.sendStaffApproved(saved.getEmail(), saved.getDisplayName());
		authAuditService.log("STAFF_APPROVED", "admin_user", saved.getId().toString(), actorEmail,
				"Approved " + saved.getEmail());

		return toResponse(saved);
	}

	@Override
	@Transactional
	public StaffMemberResponse rejectStaff(Long id, String actorEmail) {
		assertSuperAdminActor(actorEmail);
		AdminUser user = findUserOrThrow(id);
		if (user.getApprovalStatus() != AdminApprovalStatus.PENDING) {
			throw new BadRequestException("Only pending staff requests can be rejected.");
		}

		user.setApprovalStatus(AdminApprovalStatus.REJECTED);
		user.setAccountStatus(AdminAccountStatus.DISABLED);
		user.setActive(false);
		AdminUser saved = adminUserRepository.save(user);

		emailService.sendStaffRejected(saved.getEmail(), saved.getDisplayName());
		authAuditService.log("STAFF_REJECTED", "admin_user", saved.getId().toString(), actorEmail,
				"Rejected " + saved.getEmail());

		return toResponse(saved);
	}

	@Override
	@Transactional
	public void submitStaffSignup(StaffSignupRequest request) {
		if (!request.getPassword().equals(request.getConfirmPassword())) {
			throw new BadRequestException("Passwords do not match.");
		}
		PasswordPolicy.validate(request.getPassword());
		validateStaffRole(request.getRole());

		String email = normalizeEmail(request.getEmail());
		if (adminUserRepository.existsByEmailIgnoreCase(email)) {
			throw new BadRequestException("A user with this email already exists.");
		}

		AdminUser user = new AdminUser();
		user.setEmail(email);
		user.setDisplayName(request.getFullName().trim());
		user.setRole(request.getRole());
		user.setActive(true);
		user.setApprovalStatus(AdminApprovalStatus.PENDING);
		user.setAccountStatus(AdminAccountStatus.ACTIVE);
		user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
		adminUserRepository.save(user);

		emailService.sendStaffSignupReceived(email, request.getFullName().trim());
		for (AdminUser superAdmin : adminUserRepository.findAllByRole(AdminUserRole.SUPER_ADMIN)) {
			emailService.sendPlain(
					superAdmin.getEmail(),
					"New staff access request",
					"A new staff access request was submitted by "
							+ request.getFullName().trim() + " (" + email + "). "
							+ "Review pending requests in the admin portal.");
		}
		authAuditService.log("STAFF_SIGNUP_REQUESTED", "admin_user", email, email,
				"Staff signup requested for " + email);
	}

	@Override
	@Transactional
	public void setPasswordFromInvite(SetPasswordRequest request) {
		if (!request.getPassword().equals(request.getConfirmPassword())) {
			throw new BadRequestException("Passwords do not match.");
		}
		PasswordPolicy.validate(request.getPassword());

		String tokenHash = SecureTokens.hashToken(request.getToken());
		AdminUser user = adminUserRepository.findAllByApprovalStatusOrderByCreatedAtDesc(AdminApprovalStatus.PENDING)
				.stream()
				.filter(candidate -> tokenHash.equals(candidate.getInviteTokenHash()))
				.findFirst()
				.orElseThrow(() -> new BadRequestException("Invalid or expired invite link."));

		if (user.getInviteExpiresAt() == null || user.getInviteExpiresAt().isBefore(Instant.now())) {
			throw new BadRequestException("Invalid or expired invite link.");
		}

		user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
		user.setApprovalStatus(AdminApprovalStatus.APPROVED);
		user.setApprovedAt(Instant.now());
		user.setInviteTokenHash(null);
		user.setInviteExpiresAt(null);
		adminUserRepository.save(user);

		authAuditService.log("STAFF_INVITE_COMPLETED", "admin_user", user.getId().toString(), user.getEmail(),
				"Invite password set for " + user.getEmail());
	}

	@Override
	@Transactional
	public void requestPasswordReset(PasswordResetRequest request) {
		adminUserRepository.findByEmailIgnoreCase(normalizeEmail(request.getEmail())).ifPresent(user -> {
			if (user.getApprovalStatus() != AdminApprovalStatus.APPROVED || !user.isActive()) {
				return;
			}
			String rawToken = SecureTokens.generateRawToken();
			PasswordResetToken token = new PasswordResetToken();
			token.setUserId(user.getId());
			token.setTokenHash(SecureTokens.hashToken(rawToken));
			token.setExpiresAt(Instant.now().plus(1, ChronoUnit.HOURS));
			passwordResetTokenRepository.save(token);
			emailService.sendPasswordReset(user.getEmail(), frontendUrl + "/reset-password?token=" + rawToken);
			authAuditService.log("PASSWORD_RESET_REQUESTED", "admin_user", user.getId().toString(), user.getEmail(),
					"Password reset requested");
		});
	}

	@Override
	@Transactional
	public void confirmPasswordReset(PasswordResetConfirmRequest request) {
		if (!request.getPassword().equals(request.getConfirmPassword())) {
			throw new BadRequestException("Passwords do not match.");
		}
		PasswordPolicy.validate(request.getPassword());

		String tokenHash = SecureTokens.hashToken(request.getToken());
		PasswordResetToken token = passwordResetTokenRepository
				.findFirstByTokenHashAndUsedAtIsNullOrderByCreatedAtDesc(tokenHash)
				.orElseThrow(() -> new BadRequestException("Invalid or expired reset link."));

		if (token.getExpiresAt().isBefore(Instant.now())) {
			throw new BadRequestException("Invalid or expired reset link.");
		}

		AdminUser user = findUserOrThrow(token.getUserId());
		user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
		user.setFailedLoginAttempts(0);
		user.setLockoutUntil(null);
		adminUserRepository.save(user);

		token.setUsedAt(Instant.now());
		passwordResetTokenRepository.save(token);

		authAuditService.log("PASSWORD_RESET_COMPLETED", "admin_user", user.getId().toString(), user.getEmail(),
				"Password reset completed");
	}

	private void assertSuperAdminActor(String actorEmail) {
		AdminUser actor = findByEmailOrThrow(actorEmail);
		if (actor.getRole() != AdminUserRole.SUPER_ADMIN) {
			throw new UnauthorizedException("Only super admins can manage staff accounts.");
		}
	}

	private void validateStaffRole(AdminUserRole role) {
		if (role == AdminUserRole.SUPER_ADMIN) {
			throw new BadRequestException("Super admin accounts cannot be created through this flow.");
		}
	}

	private AdminUser findUserOrThrow(Long id) {
		return adminUserRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Staff member not found."));
	}

	private AdminUser findByEmailOrThrow(String email) {
		return adminUserRepository.findByEmailIgnoreCase(normalizeEmail(email))
				.orElseThrow(() -> new UnauthorizedException("Authenticated admin user not found."));
	}

	private static String normalizeEmail(String email) {
		return email.trim().toLowerCase();
	}

	private StaffMemberResponse toResponse(AdminUser entity) {
		StaffMemberResponse response = new StaffMemberResponse();
		response.setId(entity.getId());
		response.setEmail(entity.getEmail());
		response.setDisplayName(entity.getDisplayName());
		response.setRole(entity.getRole());
		response.setApprovalStatus(entity.getApprovalStatus());
		response.setActive(entity.isActive());
		response.setCreatedAt(entity.getCreatedAt());
		response.setApprovedAt(entity.getApprovedAt());
		return response;
	}
}
