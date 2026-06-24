package com.bezkoder.spring.jpa.postgresql.service;

import com.bezkoder.spring.jpa.postgresql.dto.auth.MfaSetupResponse;

public interface AdminMfaService {

	MfaSetupResponse beginEnrollment(String adminEmail);

	void enable(String adminEmail, String code);

	void disable(String adminEmail, String code);
}
