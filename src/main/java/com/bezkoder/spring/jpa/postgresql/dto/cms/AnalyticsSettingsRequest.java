package com.bezkoder.spring.jpa.postgresql.dto.cms;

import jakarta.validation.constraints.Size;

public class AnalyticsSettingsRequest {
	private boolean enabled;
	@Size(max = 100)
	private String gaMeasurementId;

	public boolean isEnabled() { return enabled; }
	public void setEnabled(boolean enabled) { this.enabled = enabled; }
	public String getGaMeasurementId() { return gaMeasurementId; }
	public void setGaMeasurementId(String gaMeasurementId) { this.gaMeasurementId = gaMeasurementId; }
}
