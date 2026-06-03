package com.bezkoder.spring.jpa.postgresql.entity;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name = "analytics_settings")
public class AnalyticsSettings {

	public static final long SINGLETON_ID = 1L;

	@Id
	private Long id = SINGLETON_ID;

	@Column(nullable = false)
	private boolean enabled = false;

	@Column(name = "ga_measurement_id", length = 100)
	private String gaMeasurementId;

	@Column(name = "updated_at", nullable = false)
	private Instant updatedAt = Instant.now();

	@PreUpdate
	void onUpdate() {
		updatedAt = Instant.now();
	}

	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }
	public boolean isEnabled() { return enabled; }
	public void setEnabled(boolean enabled) { this.enabled = enabled; }
	public String getGaMeasurementId() { return gaMeasurementId; }
	public void setGaMeasurementId(String gaMeasurementId) { this.gaMeasurementId = gaMeasurementId; }
	public Instant getUpdatedAt() { return updatedAt; }
	public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}
