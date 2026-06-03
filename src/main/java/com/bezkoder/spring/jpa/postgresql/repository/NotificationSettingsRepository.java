package com.bezkoder.spring.jpa.postgresql.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bezkoder.spring.jpa.postgresql.entity.NotificationSettings;

public interface NotificationSettingsRepository extends JpaRepository<NotificationSettings, Long> {
}
