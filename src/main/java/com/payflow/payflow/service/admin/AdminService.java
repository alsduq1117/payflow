package com.payflow.payflow.service.admin;

import com.payflow.payflow.dto.admin.DashboardMetricResponse;
import com.payflow.payflow.repository.admin.AdminMetricsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminMetricsRepository adminMetricsRepository;


    @Transactional(readOnly = true)
    public DashboardMetricResponse collectMetrics() {
        return adminMetricsRepository.getDashboardMetrics();
    }
}
