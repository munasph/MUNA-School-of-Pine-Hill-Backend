package com.bezkoder.spring.jpa.postgresql.dto.cms;

import java.time.Instant;

public class AnalyticsSettingsResponse {
	private boolean enabled;
	private String gaMeasurementId;
	private Instant updatedAt;

	public boolean isEnabled() { return enabled; }
	public void setEnabled(boolean enabled) { this.enabled = enabled; }
	public String getGaMeasurementId() { return gaMeasurementId; }
	public void setGaMeasurementId(String gaMeasurementId) { this.gaMeasurementId = gaMeasurementId; }
	public Instant getUpdatedAt() { return updatedAt; }
	public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}
