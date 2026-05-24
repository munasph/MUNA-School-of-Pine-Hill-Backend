package com.bezkoder.spring.jpa.postgresql.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bezkoder.spring.jpa.postgresql.entity.Announcement;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {

	List<Announcement> findAllByOrderByUpdatedAtDesc();

	List<Announcement> findByActiveTrueOrderByUpdatedAtDesc();

	long countByActiveTrue();
}
