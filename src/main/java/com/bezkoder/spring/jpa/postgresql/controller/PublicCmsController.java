package com.bezkoder.spring.jpa.postgresql.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bezkoder.spring.jpa.postgresql.dto.document.DocumentResponse;
import com.bezkoder.spring.jpa.postgresql.dto.event.EventResponse;
import com.bezkoder.spring.jpa.postgresql.dto.faculty.FacultyMemberResponse;
import com.bezkoder.spring.jpa.postgresql.dto.faq.FaqItemResponse;
import com.bezkoder.spring.jpa.postgresql.dto.formfield.AdmissionFormFieldResponse;
import com.bezkoder.spring.jpa.postgresql.dto.gallery.GalleryItemResponse;
import com.bezkoder.spring.jpa.postgresql.dto.news.NewsPostResponse;
import com.bezkoder.spring.jpa.postgresql.dto.seo.PageSeoResponse;
import com.bezkoder.spring.jpa.postgresql.service.AdmissionFormFieldService;
import com.bezkoder.spring.jpa.postgresql.service.SchoolDocumentService;
import com.bezkoder.spring.jpa.postgresql.service.EventService;
import com.bezkoder.spring.jpa.postgresql.service.FacultyMemberService;
import com.bezkoder.spring.jpa.postgresql.service.FaqItemService;
import com.bezkoder.spring.jpa.postgresql.service.GalleryItemService;
import com.bezkoder.spring.jpa.postgresql.service.NewsPostService;
import com.bezkoder.spring.jpa.postgresql.service.PageSeoService;

@RestController
@RequestMapping("/api")
public class PublicCmsController {

	private final EventService eventService;
	private final NewsPostService newsPostService;
	private final FacultyMemberService facultyMemberService;
	private final SchoolDocumentService documentService;
	private final GalleryItemService galleryItemService;
	private final FaqItemService faqItemService;
	private final PageSeoService pageSeoService;
	private final AdmissionFormFieldService admissionFormFieldService;

	public PublicCmsController(
			EventService eventService,
			NewsPostService newsPostService,
			FacultyMemberService facultyMemberService,
			SchoolDocumentService documentService,
			GalleryItemService galleryItemService,
			FaqItemService faqItemService,
			PageSeoService pageSeoService,
			AdmissionFormFieldService admissionFormFieldService) {
		this.eventService = eventService;
		this.newsPostService = newsPostService;
		this.facultyMemberService = facultyMemberService;
		this.documentService = documentService;
		this.galleryItemService = galleryItemService;
		this.faqItemService = faqItemService;
		this.pageSeoService = pageSeoService;
		this.admissionFormFieldService = admissionFormFieldService;
	}

	@GetMapping("/events")
	public ResponseEntity<List<EventResponse>> getPublishedEvents() {
		return ResponseEntity.ok(eventService.findPublished());
	}

	@GetMapping("/news")
	public ResponseEntity<List<NewsPostResponse>> getPublishedNews() {
		return ResponseEntity.ok(newsPostService.findPublished());
	}

	@GetMapping("/faculty")
	public ResponseEntity<List<FacultyMemberResponse>> getPublishedFaculty() {
		return ResponseEntity.ok(facultyMemberService.findPublished());
	}

	@GetMapping("/documents")
	public ResponseEntity<List<DocumentResponse>> getPublishedDocuments() {
		return ResponseEntity.ok(documentService.findPublished());
	}

	@GetMapping("/gallery")
	public ResponseEntity<List<GalleryItemResponse>> getPublishedGallery() {
		return ResponseEntity.ok(galleryItemService.findPublished());
	}

	@GetMapping("/faqs")
	public ResponseEntity<List<FaqItemResponse>> getPublishedFaqs() {
		return ResponseEntity.ok(faqItemService.findPublished());
	}

	@GetMapping("/seo/{pageKey}")
	public ResponseEntity<PageSeoResponse> getSeoByPageKey(@PathVariable String pageKey) {
		return ResponseEntity.ok(pageSeoService.findByPageKey(pageKey));
	}

	@GetMapping("/admission/form-fields")
	public ResponseEntity<List<AdmissionFormFieldResponse>> getActiveFormFields() {
		return ResponseEntity.ok(admissionFormFieldService.findPublished());
	}
}
