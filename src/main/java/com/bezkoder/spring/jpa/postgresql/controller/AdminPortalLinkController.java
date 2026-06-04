package com.bezkoder.spring.jpa.postgresql.controller;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bezkoder.spring.jpa.postgresql.dto.portal.AdminLinkResponse;
import com.bezkoder.spring.jpa.postgresql.service.AdminPortalLinkService;

/** Admin management of parent/student link requests — guarded by ROLE_ADMIN. */
@RestController
@RequestMapping("/api/admin/portal-links")
public class AdminPortalLinkController {

	private final AdminPortalLinkService linkService;

	public AdminPortalLinkController(AdminPortalLinkService linkService) {
		this.linkService = linkService;
	}

	@GetMapping
	public List<AdminLinkResponse> list() {
		return linkService.listAll();
	}

	@PostMapping("/{id}/status")
	public AdminLinkResponse setStatus(
			@PathVariable Long id,
			@RequestBody Map<String, String> body,
			Principal principal) {
		return linkService.setStatus(id, body.get("status"), principal.getName());
	}
}
