package com.bezkoder.spring.jpa.postgresql.service;

import java.util.List;

import com.bezkoder.spring.jpa.postgresql.dto.contact.ContactInquiryResponse;
import com.bezkoder.spring.jpa.postgresql.dto.contact.ContactSubmitResponse;
import com.bezkoder.spring.jpa.postgresql.dto.contact.CreateContactRequest;
import com.bezkoder.spring.jpa.postgresql.dto.contact.UpdateContactInquiryStatusRequest;
import com.bezkoder.spring.jpa.postgresql.entity.enums.ContactInquiryStatus;

public interface ContactService {

	ContactSubmitResponse sendMessage(CreateContactRequest request);

	List<ContactInquiryResponse> getAllInquiries(ContactInquiryStatus status);

	ContactInquiryResponse getInquiryById(Long id);

	ContactInquiryResponse updateInquiryStatus(Long id, UpdateContactInquiryStatusRequest request);

	void deleteInquiry(Long id);
}
