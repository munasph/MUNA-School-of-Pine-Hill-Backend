package com.bezkoder.spring.jpa.postgresql.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bezkoder.spring.jpa.postgresql.dto.portal.ForgotPasswordRequest;
import com.bezkoder.spring.jpa.postgresql.dto.portal.PortalAuthResponse;
import com.bezkoder.spring.jpa.postgresql.dto.portal.PortalLoginRequest;
import com.bezkoder.spring.jpa.postgresql.dto.portal.PortalSignupRequest;
import com.bezkoder.spring.jpa.postgresql.dto.portal.ResetPasswordRequest;
import com.bezkoder.spring.jpa.postgresql.dto.portal.TokenRequest;
import com.bezkoder.spring.jpa.postgresql.service.PortalAuthService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/portal/auth")
public class PortalAuthController {

	private final PortalAuthService portalAuthService;

	public PortalAuthController(PortalAuthService portalAuthService) {
		this.portalAuthService = portalAuthService;
	}

	@PostMapping("/signup")
	public PortalAuthResponse signup(@Valid @RequestBody PortalSignupRequest request, HttpServletRequest http) {
		return portalAuthService.signup(request, clientIp(http));
	}

	@PostMapping("/login")
	public PortalAuthResponse login(@Valid @RequestBody PortalLoginRequest request, HttpServletRequest http) {
		return portalAuthService.login(request, clientIp(http));
	}

	@PostMapping("/verify-email")
	public PortalAuthResponse verifyEmail(@Valid @RequestBody TokenRequest request) {
		return portalAuthService.verifyEmail(request.getToken());
	}

	@PostMapping("/resend-verification")
	public PortalAuthResponse resendVerification(@Valid @RequestBody ForgotPasswordRequest request) {
		return portalAuthService.resendVerification(request);
	}

	@PostMapping("/forgot-password")
	public PortalAuthResponse forgotPassword(@Valid @RequestBody ForgotPasswordRequest request, HttpServletRequest http) {
		return portalAuthService.forgotPassword(request, clientIp(http));
	}

	@PostMapping("/reset-password")
	public PortalAuthResponse resetPassword(@Valid @RequestBody ResetPasswordRequest request) {
		return portalAuthService.resetPassword(request);
	}

	private String clientIp(HttpServletRequest request) {
		String forwarded = request.getHeader("X-Forwarded-For");
		if (forwarded != null && !forwarded.isBlank()) {
			return forwarded.split(",")[0].trim();
		}
		return request.getRemoteAddr();
	}
}
