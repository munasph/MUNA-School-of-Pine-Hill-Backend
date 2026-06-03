package com.bezkoder.spring.jpa.postgresql.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bezkoder.spring.jpa.postgresql.entity.AdmissionFormField;

@Repository
public interface AdmissionFormFieldRepository extends JpaRepository<AdmissionFormField, Long> {

	List<AdmissionFormField> findAllByOrderBySortOrderAsc();
}
