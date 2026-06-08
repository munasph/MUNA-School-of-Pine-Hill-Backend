package com.bezkoder.spring.jpa.postgresql.controller.admin;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bezkoder.spring.jpa.postgresql.dto.staff.StaffInviteRequest;
import com.bezkoder.spring.jpa.postgresql.dto.staff.StaffMemberResponse;
import com.bezkoder.spring.jpa.postgresql.service.StaffService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/admin/staff")
public class AdminStaffController {

	private final StaffService staffService;

	public AdminStaffController(StaffService staffService) {
		this.staffService = staffService;
	}

	@GetMapping
	public ResponseEntity<List<StaffMemberResponse>> listActiveStaff() {
		return ResponseEntity.ok(staffService.listActiveStaff());
	}

	@GetMapping("/pending")
	public ResponseEntity<List<StaffMemberResponse>> listPendingStaff() {
		return ResponseEntity.ok(staffService.listPendingStaff());
	}

	@PostMapping("/invite")
	public ResponseEntity<StaffMemberResponse> inviteStaff(
			Authentication authentication,
			@Valid @RequestBody StaffInviteRequest request) {
		return ResponseEntity.ok(staffService.inviteStaff(request, authentication.getName()));
	}

	@PatchMapping("/{id}/approve")
	public ResponseEntity<StaffMemberResponse> approveStaff(
			Authentication authentication,
			@PathVariable Long id) {
		return ResponseEntity.ok(staffService.approveStaff(id, authentication.getName()));
	}

	@PatchMapping("/{id}/reject")
	public ResponseEntity<StaffMemberResponse> rejectStaff(
			Authentication authentication,
			@PathVariable Long id) {
		return ResponseEntity.ok(staffService.rejectStaff(id, authentication.getName()));
	}

	@GetMapping("/me")
	public ResponseEntity<Map<String, String>> currentStaff(Authentication authentication) {
		return ResponseEntity.ok(Map.of("email", authentication.getName()));
	}
}
