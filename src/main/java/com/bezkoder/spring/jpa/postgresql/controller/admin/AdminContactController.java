package com.bezkoder.spring.jpa.postgresql.controller.admin;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bezkoder.spring.jpa.postgresql.dto.contact.ContactInquiryResponse;
import com.bezkoder.spring.jpa.postgresql.dto.contact.UpdateContactInquiryStatusRequest;
import com.bezkoder.spring.jpa.postgresql.entity.enums.ContactInquiryStatus;
import com.bezkoder.spring.jpa.postgresql.service.ContactService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/admin/contacts")
public class AdminContactController {

	private final ContactService contactService;

	public AdminContactController(ContactService contactService) {
		this.contactService = contactService;
	}

	@GetMapping
	public ResponseEntity<List<ContactInquiryResponse>> getAllInquiries(
			@RequestParam(required = false) ContactInquiryStatus status) {
		return ResponseEntity.ok(contactService.getAllInquiries(status));
	}

	@GetMapping("/{id}")
	public ResponseEntity<ContactInquiryResponse> getInquiryById(@PathVariable Long id) {
		return ResponseEntity.ok(contactService.getInquiryById(id));
	}

	@PatchMapping("/{id}/status")
	public ResponseEntity<ContactInquiryResponse> updateStatus(
			@PathVariable Long id,
			@Valid @RequestBody UpdateContactInquiryStatusRequest request) {
		return ResponseEntity.ok(contactService.updateInquiryStatus(id, request));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteInquiry(@PathVariable Long id) {
		contactService.deleteInquiry(id);
		return ResponseEntity.noContent().build();
	}
}
