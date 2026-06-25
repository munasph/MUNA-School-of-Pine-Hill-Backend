package com.bezkoder.spring.jpa.postgresql.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bezkoder.spring.jpa.postgresql.entity.PasswordResetToken;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {

	Optional<PasswordResetToken> findFirstByTokenHashAndUsedAtIsNullOrderByCreatedAtDesc(String tokenHash);

	Optional<PasswordResetToken> findByTokenHash(String tokenHash);

	@Modifying
	@Query("DELETE FROM PasswordResetToken t WHERE t.userId = :userId")
	void deleteByUserId(@Param("userId") Long userId);
}
