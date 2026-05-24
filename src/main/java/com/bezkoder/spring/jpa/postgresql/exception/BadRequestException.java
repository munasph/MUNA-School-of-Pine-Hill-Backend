package com.bezkoder.spring.jpa.postgresql.exception;

public class BadRequestException extends RuntimeException {

	public BadRequestException(String message) {
		super(message);
	}
}
