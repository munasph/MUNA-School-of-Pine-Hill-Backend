package com.bezkoder.spring.jpa.postgresql.service;

import java.util.List;

import com.bezkoder.spring.jpa.postgresql.dto.portal.AdminLinkResponse;

public interface AdminPortalLinkService {

	List<AdminLinkResponse> listAll();

	AdminLinkResponse setStatus(Long linkId, String status, String adminEmail);
}
