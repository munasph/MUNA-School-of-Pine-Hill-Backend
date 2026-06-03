package com.bezkoder.spring.jpa.postgresql.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bezkoder.spring.jpa.postgresql.entity.GradeIntakeLimit;

@Repository
public interface GradeIntakeLimitRepository extends JpaRepository<GradeIntakeLimit, Long> {

	List<GradeIntakeLimit> findAllByOrderByCreatedAtDesc();
}
