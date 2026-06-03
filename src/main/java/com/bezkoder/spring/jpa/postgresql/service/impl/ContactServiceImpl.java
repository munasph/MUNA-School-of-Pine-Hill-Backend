package com.bezkoder.spring.jpa.postgresql.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bezkoder.spring.jpa.postgresql.dto.contact.ContactInquiryResponse;
import com.bezkoder.spring.jpa.postgresql.dto.contact.ContactSubmitResponse;
import com.bezkoder.spring.jpa.postgresql.dto.contact.CreateContactRequest;
import com.bezkoder.spring.jpa.postgresql.dto.contact.UpdateContactInquiryStatusRequest;
import com.bezkoder.spring.jpa.postgresql.entity.ContactInquiry;
import com.bezkoder.spring.jpa.postgresql.entity.enums.ContactInquiryStatus;
import com.bezkoder.spring.jpa.postgresql.exception.ResourceNotFoundException;
import com.bezkoder.spring.jpa.postgresql.repository.ContactInquiryRepository;
import com.bezkoder.spring.jpa.postgresql.service.ContactService;

@Service
@Transactional(readOnly = true)
public class ContactServiceImpl implements ContactService {

	private final ContactInquiryRepository contactRepository;

	public ContactServiceImpl(ContactInquiryRepository contactRepository) {
		this.contactRepository = contactRepository;
	}

	@Override
	@Transactional
	public ContactSubmitResponse sendMessage(CreateContactRequest request) {
		ContactInquiry saved = contactRepository.save(toEntity(request));

		return new ContactSubmitResponse(
				true,
				saved.getMessageId(),
				"Message received. We will reach out within 24 hours.");
	}

	@Override
	public List<ContactInquiryResponse> getAllInquiries(ContactInquiryStatus status) {
		List<ContactInquiry> inquiries = status == null
				? contactRepository.findAllByOrderBySubmittedAtDesc()
				: contactRepository.findAllByStatusOrderBySubmittedAtDesc(status);
		return inquiries.stream().map(this::toResponse).toList();
	}

	@Override
	public ContactInquiryResponse getInquiryById(Long id) {
		return toResponse(findInquiryOrThrow(id));
	}

	@Override
	@Transactional
	public ContactInquiryResponse updateInquiryStatus(Long id, UpdateContactInquiryStatusRequest request) {
		ContactInquiry entity = findInquiryOrThrow(id);
		entity.setStatus(request.getStatus());
		return toResponse(contactRepository.save(entity));
	}

	@Override
	@Transactional
	public void deleteInquiry(Long id) {
		if (!contactRepository.existsById(id)) {
			throw new ResourceNotFoundException("Inquiry not found with id: " + id);
		}
		contactRepository.deleteById(id);
	}

	private ContactInquiry findInquiryOrThrow(Long id) {
		return contactRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Inquiry not found with id: " + id));
	}

	private ContactInquiry toEntity(CreateContactRequest request) {
		ContactInquiry entity = new ContactInquiry();
		entity.setMessageId("MSG-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
		entity.setName(request.getName());
		entity.setEmail(request.getEmail());
		entity.setSubject(request.getSubject());
		entity.setMessage(request.getMessage());
		return entity;
	}

	private ContactInquiryResponse toResponse(ContactInquiry entity) {
		ContactInquiryResponse response = new ContactInquiryResponse();
		response.setId(entity.getId());
		response.setMessageId(entity.getMessageId());
		response.setName(entity.getName());
		response.setEmail(entity.getEmail());
		response.setSubject(entity.getSubject());
		response.setMessage(entity.getMessage());
		response.setStatus(entity.getStatus());
		response.setSubmittedAt(entity.getSubmittedAt());
		return response;
	}
}
