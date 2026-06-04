package com.bezkoder.spring.jpa.postgresql.service;

import com.bezkoder.spring.jpa.postgresql.dto.portal.LinkRequestPayload;
import com.bezkoder.spring.jpa.postgresql.dto.portal.PortalProfileResponse;

public interface PortalService {

	PortalProfileResponse getProfile(String email);

	PortalProfileResponse requestLink(String email, LinkRequestPayload payload);
}
