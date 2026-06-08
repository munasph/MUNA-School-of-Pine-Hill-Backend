package com.bezkoder.spring.jpa.postgresql.service;

import java.util.List;

import com.bezkoder.spring.jpa.postgresql.dto.auth.PasswordResetConfirmRequest;
import com.bezkoder.spring.jpa.postgresql.dto.auth.PasswordResetRequest;
import com.bezkoder.spring.jpa.postgresql.dto.auth.SetPasswordRequest;
import com.bezkoder.spring.jpa.postgresql.dto.auth.StaffSignupRequest;
import com.bezkoder.spring.jpa.postgresql.dto.staff.StaffInviteRequest;
import com.bezkoder.spring.jpa.postgresql.dto.staff.StaffMemberResponse;

public interface StaffService {

	List<StaffMemberResponse> listActiveStaff();

	List<StaffMemberResponse> listPendingStaff();

	StaffMemberResponse inviteStaff(StaffInviteRequest request, String actorEmail);

	StaffMemberResponse approveStaff(Long id, String actorEmail);

	StaffMemberResponse rejectStaff(Long id, String actorEmail);

	void submitStaffSignup(StaffSignupRequest request);

	void setPasswordFromInvite(SetPasswordRequest request);

	void requestPasswordReset(PasswordResetRequest request);

	void confirmPasswordReset(PasswordResetConfirmRequest request);
}
