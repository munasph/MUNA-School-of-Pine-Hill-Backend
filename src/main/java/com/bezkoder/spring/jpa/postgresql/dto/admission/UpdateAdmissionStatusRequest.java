package com.bezkoder.spring.jpa.postgresql.dto.admission;

import com.bezkoder.spring.jpa.postgresql.entity.enums.ApplicationStatus;

import jakarta.validation.constraints.NotNull;

public class UpdateAdmissionStatusRequest {

	@NotNull
	private ApplicationStatus status;

	public ApplicationStatus getStatus() {
		return status;
	}

	public void setStatus(ApplicationStatus status) {
		this.status = status;
	}
}
