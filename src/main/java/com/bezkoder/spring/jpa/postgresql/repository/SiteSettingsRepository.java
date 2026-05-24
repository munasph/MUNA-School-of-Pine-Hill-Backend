package com.bezkoder.spring.jpa.postgresql.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bezkoder.spring.jpa.postgresql.entity.SiteSettings;

@Repository
public interface SiteSettingsRepository extends JpaRepository<SiteSettings, Long> {
}
