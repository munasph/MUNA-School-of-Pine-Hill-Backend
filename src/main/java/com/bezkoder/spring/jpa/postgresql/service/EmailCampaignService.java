package com.bezkoder.spring.jpa.postgresql.service;

import java.util.List;

import com.bezkoder.spring.jpa.postgresql.dto.campaign.EmailCampaignRequest;
import com.bezkoder.spring.jpa.postgresql.dto.campaign.EmailCampaignResponse;

public interface EmailCampaignService {

	List<EmailCampaignResponse> findAll();

	List<EmailCampaignResponse> findPublished();

	EmailCampaignResponse findById(Long id);

	EmailCampaignResponse create(EmailCampaignRequest request);

	EmailCampaignResponse update(Long id, EmailCampaignRequest request);

	void delete(Long id);
}
