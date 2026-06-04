package com.bezkoder.spring.jpa.postgresql.service;

import com.bezkoder.spring.jpa.postgresql.dto.auth.AuthResponse;
import com.bezkoder.spring.jpa.postgresql.dto.auth.LoginRequest;
import com.bezkoder.spring.jpa.postgresql.dto.auth.SignupRequest;

public interface AuthService {

	AuthResponse login(LoginRequest request, String clientIp);

	AuthResponse signup(SignupRequest request);
}
