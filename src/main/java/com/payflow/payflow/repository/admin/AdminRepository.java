package com.payflow.payflow.repository.admin;

import com.payflow.payflow.dto.admin.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface AdminRepository {
    DashboardMetricResponse getDashboardMetrics();

    PageImpl<OrderSummaryResponse> getOrderPage(OrderPageRequest orderPageRequest);

    SettlementOverviewResponse getSettlementOverview(LocalDateTime start, LocalDateTime end);

    Page<SettlementResponse> getSettlement(LocalDateTime start, LocalDateTime end, Pageable pageable);
}
