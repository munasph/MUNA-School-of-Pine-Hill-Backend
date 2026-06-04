package com.bezkoder.spring.jpa.postgresql.controller;

import java.security.Principal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bezkoder.spring.jpa.postgresql.dto.portal.LinkRequestPayload;
import com.bezkoder.spring.jpa.postgresql.dto.portal.PortalProfileResponse;
import com.bezkoder.spring.jpa.postgresql.service.PortalService;

import jakarta.validation.Valid;

/** Authenticated family-portal data — guarded by PARENT/STUDENT role in SecurityConfig. */
@RestController
@RequestMapping("/api/portal")
public class PortalController {

	private final PortalService portalService;

	public PortalController(PortalService portalService) {
		this.portalService = portalService;
	}

	@GetMapping("/me")
	public PortalProfileResponse me(Principal principal) {
		return portalService.getProfile(principal.getName());
	}

	@PostMapping("/link-requests")
	public PortalProfileResponse requestLink(@Valid @RequestBody LinkRequestPayload payload, Principal principal) {
		return portalService.requestLink(principal.getName(), payload);
	}
}
