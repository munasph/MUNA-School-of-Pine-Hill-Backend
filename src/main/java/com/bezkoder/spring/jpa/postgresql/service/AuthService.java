package com.bezkoder.spring.jpa.postgresql.service;

import com.bezkoder.spring.jpa.postgresql.dto.auth.AuthResponse;
import com.bezkoder.spring.jpa.postgresql.dto.auth.LoginRequest;

public interface AuthService {

	AuthResponse login(LoginRequest request);
}
