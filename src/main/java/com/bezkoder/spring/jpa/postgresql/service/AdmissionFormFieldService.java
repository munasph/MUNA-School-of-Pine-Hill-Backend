package com.bezkoder.spring.jpa.postgresql.service;

import java.util.List;

import com.bezkoder.spring.jpa.postgresql.dto.formfield.AdmissionFormFieldRequest;
import com.bezkoder.spring.jpa.postgresql.dto.formfield.AdmissionFormFieldResponse;

public interface AdmissionFormFieldService {

	List<AdmissionFormFieldResponse> findAll();

	List<AdmissionFormFieldResponse> findPublished();

	AdmissionFormFieldResponse findById(Long id);

	AdmissionFormFieldResponse create(AdmissionFormFieldRequest request);

	AdmissionFormFieldResponse update(Long id, AdmissionFormFieldRequest request);

	void delete(Long id);
}
