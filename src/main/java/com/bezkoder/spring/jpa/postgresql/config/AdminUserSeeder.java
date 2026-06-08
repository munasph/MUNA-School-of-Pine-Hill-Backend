package com.bezkoder.spring.jpa.postgresql.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.bezkoder.spring.jpa.postgresql.entity.AdminUser;
import com.bezkoder.spring.jpa.postgresql.entity.enums.AdminAccountStatus;
import com.bezkoder.spring.jpa.postgresql.entity.enums.AdminApprovalStatus;
import com.bezkoder.spring.jpa.postgresql.entity.enums.AdminUserRole;
import com.bezkoder.spring.jpa.postgresql.repository.AdminUserRepository;

@Component
public class AdminUserSeeder implements ApplicationRunner {

	private static final Logger log = LoggerFactory.getLogger(AdminUserSeeder.class);

	private final AdminUserRepository adminUserRepository;
	private final PasswordEncoder passwordEncoder;
	private final String adminEmail;
	private final String adminPassword;
	private final String adminEmail2;
	private final String adminPassword2;
	private final String adminEmail3;
	private final String adminPassword3;

	public AdminUserSeeder(
			AdminUserRepository adminUserRepository,
			PasswordEncoder passwordEncoder,
			@Value("${app.admin.email:}") String adminEmail,
			@Value("${app.admin.password:}") String adminPassword,
			@Value("${app.admin.email-2:}") String adminEmail2,
			@Value("${app.admin.password-2:}") String adminPassword2,
			@Value("${app.admin.email-3:}") String adminEmail3,
			@Value("${app.admin.password-3:}") String adminPassword3) {
		this.adminUserRepository = adminUserRepository;
		this.passwordEncoder = passwordEncoder;
		this.adminEmail = adminEmail;
		this.adminPassword = adminPassword;
		this.adminEmail2 = adminEmail2;
		this.adminPassword2 = adminPassword2;
		this.adminEmail3 = adminEmail3;
		this.adminPassword3 = adminPassword3;
	}

	@Override
	public void run(ApplicationArguments args) {
		seedSuperAdmin(adminEmail, adminPassword, "Primary Super Admin");
		seedSuperAdmin(adminEmail2, adminPassword2, "Super Admin 2");
		seedSuperAdmin(adminEmail3, adminPassword3, "Super Admin 3");
	}

	private void seedSuperAdmin(String email, String password, String displayName) {
		if (email == null || email.isBlank() || password == null || password.isBlank()) {
			return;
		}

		AdminUser user = adminUserRepository.findByEmailIgnoreCase(email.trim())
				.orElseGet(AdminUser::new);

		user.setEmail(email.trim().toLowerCase());
		user.setPasswordHash(passwordEncoder.encode(password));
		user.setDisplayName(displayName);
		user.setRole(AdminUserRole.SUPER_ADMIN);
		user.setActive(true);
		user.setApprovalStatus(AdminApprovalStatus.APPROVED);
		user.setAccountStatus(AdminAccountStatus.ACTIVE);
		user.setInviteTokenHash(null);
		user.setInviteExpiresAt(null);
		adminUserRepository.save(user);
		log.info("Ensured super admin account exists for {}", user.getEmail());
	}
}
