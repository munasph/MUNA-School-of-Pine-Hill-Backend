package com.bezkoder.spring.jpa.postgresql.controller.admin;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bezkoder.spring.jpa.postgresql.dto.cms.CmsModuleInfo;

@RestController
@RequestMapping("/api/admin/modules")
public class AdminCmsModulesController {

	@GetMapping
	public ResponseEntity<List<CmsModuleInfo>> listModules() {
		return ResponseEntity.ok(List.of(
				module("admissions", "Admissions", "Application submissions", "/admin/admissions", "/api/admin/admissions", false),
				module("announcements", "Announcements", "Homepage banner messages", "/admin/announcements", "/api/admin/announcements", false),
				module("inquiries", "Inquiries", "Contact form inbox", "/admin/inquiries", "/api/admin/contacts", false),
				module("settings", "Site settings", "School info and toggles", "/admin/settings", "/api/admin/site-settings", false),
				module("events", "Events", "Calendar events", "/admin/modules/events", "/api/admin/events", true),
				module("news", "News", "News and blog posts", "/admin/modules/news", "/api/admin/news", true),
				module("faculty", "Faculty", "Staff directory", "/admin/modules/faculty", "/api/admin/faculty", true),
				module("documents", "Documents", "Downloadable files", "/admin/modules/documents", "/api/admin/documents", true),
				module("gallery", "Gallery", "Photo gallery", "/admin/modules/gallery", "/api/admin/gallery", true),
				module("faqs", "FAQs", "Frequently asked questions", "/admin/modules/faqs", "/api/admin/faqs", true),
				module("media", "Media library", "Uploaded assets", "/admin/modules/media", "/api/admin/media", true),
				module("seo", "Page SEO", "Per-page metadata", "/admin/modules/seo", "/api/admin/seo", true),
				module("users", "Admin users", "Admin user accounts", "/admin/modules/users", "/api/admin/users", true),
				module("intake-limits", "Intake limits", "Grade capacity", "/admin/modules/intake-limits", "/api/admin/intake-limits", true),
				module("form-fields", "Form fields", "Admission form config", "/admin/modules/form-fields", "/api/admin/form-fields", true),
				module("inquiry-templates", "Reply templates", "Canned contact replies", "/admin/modules/inquiry-templates", "/api/admin/inquiry-templates", true),
				module("email-campaigns", "Email campaigns", "Newsletter drafts", "/admin/modules/email-campaigns", "/api/admin/email-campaigns", true),
				module("notifications", "Notifications", "Email alert settings", "/admin/modules/notifications", "/api/admin/notifications", true),
				module("analytics", "Analytics", "GA measurement ID", "/admin/modules/analytics", "/api/admin/analytics", true),
				module("audit-logs", "Audit log", "Change history", "/admin/modules/audit-logs", "/api/admin/audit-logs", true)
		));
	}

	private static CmsModuleInfo module(
			String key, String label, String description,
			String adminPath, String apiPath, boolean scaffoldOnly) {
		CmsModuleInfo info = new CmsModuleInfo();
		info.setKey(key);
		info.setLabel(label);
		info.setDescription(description);
		info.setAdminPath(adminPath);
		info.setApiPath(apiPath);
		info.setScaffoldOnly(scaffoldOnly);
		return info;
	}
}
