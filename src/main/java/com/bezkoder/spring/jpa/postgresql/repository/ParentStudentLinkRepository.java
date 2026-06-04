package com.bezkoder.spring.jpa.postgresql.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bezkoder.spring.jpa.postgresql.entity.ParentStudentLink;

@Repository
public interface ParentStudentLinkRepository extends JpaRepository<ParentStudentLink, Long> {
	List<ParentStudentLink> findByPortalUserId(Long portalUserId);

	List<ParentStudentLink> findByPortalUserIdAndStatus(Long portalUserId, String status);

	boolean existsByPortalUserIdAndApplicationId(Long portalUserId, Long applicationId);
}
