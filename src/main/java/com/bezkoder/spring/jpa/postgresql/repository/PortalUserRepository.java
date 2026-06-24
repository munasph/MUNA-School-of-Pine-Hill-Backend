package com.bezkoder.spring.jpa.postgresql.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bezkoder.spring.jpa.postgresql.entity.PortalUser;

@Repository
public interface PortalUserRepository extends JpaRepository<PortalUser, Long> {
	Optional<PortalUser> findByEmailIgnoreCase(String email);

	boolean existsByEmailIgnoreCase(String email);
}
