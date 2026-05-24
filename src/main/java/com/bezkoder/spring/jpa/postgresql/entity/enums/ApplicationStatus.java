package com.bezkoder.spring.jpa.postgresql.entity.enums;

/** Stored in {@code admission_applications.status} column. */
public enum ApplicationStatus {
	PENDING,
	REVIEWED,
	ACCEPTED,
	REJECTED
}
