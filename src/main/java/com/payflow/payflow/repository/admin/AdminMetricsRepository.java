package com.payflow.payflow.repository.admin;

import com.payflow.payflow.dto.admin.DashboardMetricResponse;

public interface AdminMetricsRepository {
    DashboardMetricResponse getDashboardMetrics();
}
