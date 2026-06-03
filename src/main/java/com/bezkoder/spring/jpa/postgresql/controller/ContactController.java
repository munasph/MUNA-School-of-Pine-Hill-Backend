package com.bezkoder.spring.jpa.postgresql.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bezkoder.spring.jpa.postgresql.dto.contact.ContactSubmitResponse;
import com.bezkoder.spring.jpa.postgresql.dto.contact.CreateContactRequest;
import com.bezkoder.spring.jpa.postgresql.service.ContactService;

import jakarta.validation.Valid;

/** Public contact / inquiry form endpoint — matches Angular {@code POST /api/contact}. */
@RestController
@RequestMapping("/api/contact")
public class ContactController {

	private final ContactService contactService;

	public ContactController(ContactService contactService) {
		this.contactService = contactService;
	}

	@PostMapping
	public ResponseEntity<ContactSubmitResponse> sendMessage(
			@Valid @RequestBody CreateContactRequest request) {
		ContactSubmitResponse response = contactService.sendMessage(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
}
