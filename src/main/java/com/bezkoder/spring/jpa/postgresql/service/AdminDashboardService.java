package com.bezkoder.spring.jpa.postgresql.service;

import com.bezkoder.spring.jpa.postgresql.dto.admin.AdminDashboardResponse;

public interface AdminDashboardService {

	AdminDashboardResponse getDashboardSummary();
}
