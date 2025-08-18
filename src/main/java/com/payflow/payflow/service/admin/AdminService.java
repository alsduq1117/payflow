package com.payflow.payflow.service.admin;

import com.payflow.payflow.dto.admin.*;
import com.payflow.payflow.dto.common.PagingResponse;
import com.payflow.payflow.repository.admin.AdminMetricsRepository;
import com.payflow.payflow.repository.admin.AdminOrdersRepository;
import com.payflow.payflow.repository.admin.AdminSettlementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminMetricsRepository adminMetricsRepository;
    private final AdminOrdersRepository adminOrdersRepository;
    private final AdminSettlementRepository adminSettlementRepository;


    @Transactional(readOnly = true)
    public DashboardMetricResponse collectMetrics() {
        return adminMetricsRepository.getDashboardMetrics();
    }

    @Transactional(readOnly = true)
    public PagingResponse<OrderSummaryResponse> searchOrders(OrderPageRequest orderPageRequest) {
        PageImpl<OrderSummaryResponse> orderSummaryPage =  adminOrdersRepository.getOrderPage(orderPageRequest);
        return new PagingResponse<>(orderSummaryPage);
    }

    @Transactional(readOnly = true)
    public SettlementOverviewResponse getSettlementOverview(LocalDateTime start, LocalDateTime end) {
        return adminSettlementRepository.getSettlementOverview(start, end);
    }

    public Page<SettlementResponse> getSettlement(LocalDateTime start, LocalDateTime end, Pageable pageable) {
        return adminSettlementRepository.getSettlement(start,end,pageable);
    }
}
