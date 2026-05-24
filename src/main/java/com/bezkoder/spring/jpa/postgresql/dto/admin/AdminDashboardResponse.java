package com.bezkoder.spring.jpa.postgresql.dto.admin;

public class AdminDashboardResponse {

	private long totalApplications;
	private long pendingApplications;
	private long activeAnnouncements;

	public long getTotalApplications() {
		return totalApplications;
	}

	public void setTotalApplications(long totalApplications) {
		this.totalApplications = totalApplications;
	}

	public long getPendingApplications() {
		return pendingApplications;
	}

	public void setPendingApplications(long pendingApplications) {
		this.pendingApplications = pendingApplications;
	}

	public long getActiveAnnouncements() {
		return activeAnnouncements;
	}

	public void setActiveAnnouncements(long activeAnnouncements) {
		this.activeAnnouncements = activeAnnouncements;
	}
}
