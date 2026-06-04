package com.bezkoder.spring.jpa.postgresql.bootstrap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.bezkoder.spring.jpa.postgresql.entity.AdminUser;
import com.bezkoder.spring.jpa.postgresql.entity.enums.AdminUserRole;
import com.bezkoder.spring.jpa.postgresql.repository.AdminUserRepository;

/**
 * Ensures a bootstrap admin exists. On first boot (empty admin_users table) it creates an
 * admin from ADMIN_EMAIL / ADMIN_PASSWORD env vars with a BCrypt-hashed password.
 * The plaintext password is never stored. Once seeded, change the password via the DB or
 * a future admin-management screen and rotate ADMIN_PASSWORD.
 */
@Component
public class AdminUserSeeder implements CommandLineRunner {

	private static final Logger log = LoggerFactory.getLogger(AdminUserSeeder.class);

	private final AdminUserRepository adminUserRepository;
	private final PasswordEncoder passwordEncoder;
	private final String adminEmail;
	private final String adminPassword;

	public AdminUserSeeder(
			AdminUserRepository adminUserRepository,
			PasswordEncoder passwordEncoder,
			@Value("${app.admin.email:}") String adminEmail,
			@Value("${app.admin.password:}") String adminPassword) {
		this.adminUserRepository = adminUserRepository;
		this.passwordEncoder = passwordEncoder;
		this.adminEmail = adminEmail;
		this.adminPassword = adminPassword;
	}

	@Override
	public void run(String... args) {
		if (adminEmail == null || adminEmail.isBlank() || adminPassword == null || adminPassword.isBlank()) {
			log.info("No ADMIN_EMAIL/ADMIN_PASSWORD configured — skipping admin seed.");
			return;
		}

		String email = adminEmail.trim().toLowerCase();
		if (adminUserRepository.existsByEmailIgnoreCase(email)) {
			return;
		}

		AdminUser admin = new AdminUser();
		admin.setEmail(email);
		admin.setPasswordHash(passwordEncoder.encode(adminPassword));
		admin.setDisplayName("Administrator");
		admin.setRole(AdminUserRole.ADMIN);
		admin.setActive(true);
		adminUserRepository.save(admin);

		log.info("Seeded bootstrap admin account: {}", email);
	}
}
