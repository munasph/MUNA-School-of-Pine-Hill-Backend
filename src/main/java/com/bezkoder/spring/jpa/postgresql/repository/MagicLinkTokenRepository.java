package com.bezkoder.spring.jpa.postgresql.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bezkoder.spring.jpa.postgresql.entity.MagicLinkToken;

@Repository
public interface MagicLinkTokenRepository extends JpaRepository<MagicLinkToken, Long> {
	Optional<MagicLinkToken> findByTokenHash(String tokenHash);

	@Modifying
	@Transactional
	@Query("delete from MagicLinkToken t where t.userId = :userId")
	void deleteByUserId(@Param("userId") Long userId);
}
