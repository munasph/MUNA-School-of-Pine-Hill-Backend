package com.bezkoder.spring.jpa.postgresql.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bezkoder.spring.jpa.postgresql.entity.AdminUser;

public interface AdminUserRepository extends JpaRepository<AdminUser, Long> {
	List<AdminUser> findAllByOrderByEmailAsc();
}
