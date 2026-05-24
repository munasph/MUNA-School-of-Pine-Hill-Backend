package com.bezkoder.spring.jpa.postgresql.dto.admission;

/** Matches Angular {@code AdmissionSubmitResponse}. */
public class AdmissionSubmitResponse {

	private boolean success;
	private String applicationId;
	private String message;

	public AdmissionSubmitResponse() {
	}

	public AdmissionSubmitResponse(boolean success, String applicationId, String message) {
		this.success = success;
		this.applicationId = applicationId;
		this.message = message;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
