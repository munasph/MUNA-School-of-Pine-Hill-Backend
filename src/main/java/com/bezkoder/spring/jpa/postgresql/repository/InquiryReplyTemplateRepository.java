package com.bezkoder.spring.jpa.postgresql.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bezkoder.spring.jpa.postgresql.entity.InquiryReplyTemplate;

@Repository
public interface InquiryReplyTemplateRepository extends JpaRepository<InquiryReplyTemplate, Long> {

	List<InquiryReplyTemplate> findAllByOrderByCreatedAtDesc();
}
