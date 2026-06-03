package com.bezkoder.spring.jpa.postgresql.service;

import java.util.List;

import com.bezkoder.spring.jpa.postgresql.dto.inquiry.InquiryReplyTemplateRequest;
import com.bezkoder.spring.jpa.postgresql.dto.inquiry.InquiryReplyTemplateResponse;

public interface InquiryReplyTemplateService {

	List<InquiryReplyTemplateResponse> findAll();

	List<InquiryReplyTemplateResponse> findPublished();

	InquiryReplyTemplateResponse findById(Long id);

	InquiryReplyTemplateResponse create(InquiryReplyTemplateRequest request);

	InquiryReplyTemplateResponse update(Long id, InquiryReplyTemplateRequest request);

	void delete(Long id);
}
