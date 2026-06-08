package com.bezkoder.spring.jpa.postgresql.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bezkoder.spring.jpa.postgresql.dto.auth.AuthResponse;
import com.bezkoder.spring.jpa.postgresql.dto.auth.LoginRequest;
import com.bezkoder.spring.jpa.postgresql.dto.auth.PasswordResetConfirmRequest;
import com.bezkoder.spring.jpa.postgresql.dto.auth.PasswordResetRequest;
import com.bezkoder.spring.jpa.postgresql.dto.auth.SetPasswordRequest;
import com.bezkoder.spring.jpa.postgresql.dto.auth.SignupRequest;
import com.bezkoder.spring.jpa.postgresql.dto.auth.StaffSignupRequest;
import com.bezkoder.spring.jpa.postgresql.service.AuthService;
import com.bezkoder.spring.jpa.postgresql.service.StaffService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private final AuthService authService;
	private final StaffService staffService;

	public AuthController(AuthService authService, StaffService staffService) {
		this.authService = authService;
		this.staffService = staffService;
	}

	@PostMapping("/login")
	public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
		return ResponseEntity.ok(authService.login(request));
	}

	@PostMapping("/signup")
	public ResponseEntity<AuthResponse> signup(@Valid @RequestBody SignupRequest request) {
		return ResponseEntity.ok(authService.signup(request));
	}

	@PostMapping("/staff-signup")
	public ResponseEntity<AuthResponse> staffSignup(@Valid @RequestBody StaffSignupRequest request) {
		staffService.submitStaffSignup(request);
		AuthResponse response = new AuthResponse(true,
				"Your request has been submitted. You will receive an email when it is approved.");
		return ResponseEntity.ok(response);
	}

	@PostMapping("/set-password")
	public ResponseEntity<AuthResponse> setPassword(@Valid @RequestBody SetPasswordRequest request) {
		staffService.setPasswordFromInvite(request);
		return ResponseEntity.ok(new AuthResponse(true, "Password set successfully. You can now log in."));
	}

	@PostMapping("/password-reset")
	public ResponseEntity<AuthResponse> requestPasswordReset(@Valid @RequestBody PasswordResetRequest request) {
		staffService.requestPasswordReset(request);
		return ResponseEntity.ok(new AuthResponse(true,
				"If an account exists for that email, a reset link has been sent."));
	}

	@PostMapping("/password-reset/confirm")
	public ResponseEntity<AuthResponse> confirmPasswordReset(@Valid @RequestBody PasswordResetConfirmRequest request) {
		staffService.confirmPasswordReset(request);
		return ResponseEntity.ok(new AuthResponse(true, "Password reset successfully. You can now log in."));
	}
}
