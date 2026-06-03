package com.bezkoder.spring.jpa.postgresql.dto.contact;

/** Matches Angular {@code ContactSubmitResponse}. */
public class ContactSubmitResponse {

	private boolean success;
	private String messageId;
	private String message;

	public ContactSubmitResponse() {
	}

	public ContactSubmitResponse(boolean success, String messageId, String message) {
		this.success = success;
		this.messageId = messageId;
		this.message = message;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
