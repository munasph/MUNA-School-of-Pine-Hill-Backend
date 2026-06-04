package com.bezkoder.spring.jpa.postgresql.service;

import com.bezkoder.spring.jpa.postgresql.dto.portal.ForgotPasswordRequest;
import com.bezkoder.spring.jpa.postgresql.dto.portal.PortalAuthResponse;
import com.bezkoder.spring.jpa.postgresql.dto.portal.PortalLoginRequest;
import com.bezkoder.spring.jpa.postgresql.dto.portal.PortalSignupRequest;
import com.bezkoder.spring.jpa.postgresql.dto.portal.ResetPasswordRequest;

public interface PortalAuthService {

	PortalAuthResponse signup(PortalSignupRequest request, String clientIp);

	PortalAuthResponse login(PortalLoginRequest request, String clientIp);

	PortalAuthResponse verifyEmail(String rawToken);

	PortalAuthResponse resendVerification(ForgotPasswordRequest request);

	PortalAuthResponse forgotPassword(ForgotPasswordRequest request, String clientIp);

	PortalAuthResponse resetPassword(ResetPasswordRequest request);
}
