package com.bezkoder.spring.jpa.postgresql.controller;

import java.security.Principal;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bezkoder.spring.jpa.postgresql.dto.auth.MfaSetupResponse;
import com.bezkoder.spring.jpa.postgresql.service.AdminMfaService;

/** Admin TOTP MFA enrollment — guarded by ROLE_ADMIN. */
@RestController
@RequestMapping("/api/admin/mfa")
public class AdminMfaController {

	private final AdminMfaService mfaService;

	public AdminMfaController(AdminMfaService mfaService) {
		this.mfaService = mfaService;
	}

	@PostMapping("/setup")
	public MfaSetupResponse setup(Principal principal) {
		return mfaService.beginEnrollment(principal.getName());
	}

	@PostMapping("/enable")
	public Map<String, Object> enable(@RequestBody Map<String, String> body, Principal principal) {
		mfaService.enable(principal.getName(), body.get("code"));
		return Map.of("success", true, "message", "Two-factor authentication enabled.");
	}

	@PostMapping("/disable")
	public Map<String, Object> disable(@RequestBody Map<String, String> body, Principal principal) {
		mfaService.disable(principal.getName(), body.get("code"));
		return Map.of("success", true, "message", "Two-factor authentication disabled.");
	}
}
