package com.bezkoder.spring.jpa.postgresql.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bezkoder.spring.jpa.postgresql.entity.PasswordResetToken;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {

	Optional<PasswordResetToken> findFirstByTokenHashAndUsedAtIsNullOrderByCreatedAtDesc(String tokenHash);
}
