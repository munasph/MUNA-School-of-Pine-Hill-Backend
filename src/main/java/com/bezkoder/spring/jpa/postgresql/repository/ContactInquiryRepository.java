package com.bezkoder.spring.jpa.postgresql.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bezkoder.spring.jpa.postgresql.entity.ContactInquiry;
import com.bezkoder.spring.jpa.postgresql.entity.enums.ContactInquiryStatus;

@Repository
public interface ContactInquiryRepository extends JpaRepository<ContactInquiry, Long> {

	List<ContactInquiry> findAllByOrderBySubmittedAtDesc();

	List<ContactInquiry> findAllByStatusOrderBySubmittedAtDesc(ContactInquiryStatus status);

	long countByStatus(ContactInquiryStatus status);
}
