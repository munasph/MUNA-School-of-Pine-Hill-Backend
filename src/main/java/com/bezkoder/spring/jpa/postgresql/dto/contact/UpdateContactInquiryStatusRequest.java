package com.bezkoder.spring.jpa.postgresql.dto.contact;

import com.bezkoder.spring.jpa.postgresql.entity.enums.ContactInquiryStatus;

import jakarta.validation.constraints.NotNull;

public class UpdateContactInquiryStatusRequest {

	@NotNull
	private ContactInquiryStatus status;

	public ContactInquiryStatus getStatus() {
		return status;
	}

	public void setStatus(ContactInquiryStatus status) {
		this.status = status;
	}
}
