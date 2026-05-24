package com.bezkoder.spring.jpa.postgresql.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.bezkoder.spring.jpa.postgresql.model.Announcement;
import com.bezkoder.spring.jpa.postgresql.model.SiteSettings;
import com.bezkoder.spring.jpa.postgresql.repository.AnnouncementRepository;
import com.bezkoder.spring.jpa.postgresql.repository.SiteSettingsRepository;

/** Seeds default CMS data when the database is empty. */
@Component
public class DataInitializer implements CommandLineRunner {

	private final SiteSettingsRepository siteSettingsRepository;
	private final AnnouncementRepository announcementRepository;

	public DataInitializer(
			SiteSettingsRepository siteSettingsRepository,
			AnnouncementRepository announcementRepository) {
		this.siteSettingsRepository = siteSettingsRepository;
		this.announcementRepository = announcementRepository;
	}

	@Override
	public void run(String... args) {
		if (siteSettingsRepository.findById(SiteSettings.SINGLETON_ID).isEmpty()) {
			siteSettingsRepository.save(new SiteSettings());
		}

		if (announcementRepository.count() == 0) {
			Announcement announcement = new Announcement();
			announcement.setEmoji("🎓");
			announcement.setTitle("Announcement Title");
			announcement.setSubtitle("Short subtitle");
			announcement.setCta("Learn More");
			announcement.setHref("/admission");
			announcement.setActive(true);
			announcementRepository.save(announcement);
		}
	}
}
