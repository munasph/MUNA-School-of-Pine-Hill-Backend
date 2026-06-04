package com.bezkoder.spring.jpa.postgresql.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bezkoder.spring.jpa.postgresql.entity.AdminUser;

public interface AdminUserRepository extends JpaRepository<AdminUser, Long> {
	List<AdminUser> findAllByOrderByEmailAsc();

	Optional<AdminUser> findByEmailIgnoreCase(String email);

	boolean existsByEmailIgnoreCase(String email);
}
