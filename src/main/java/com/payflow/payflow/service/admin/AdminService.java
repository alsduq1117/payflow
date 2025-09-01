package com.payflow.payflow.service.admin;

import com.payflow.payflow.dto.admin.*;
import com.payflow.payflow.dto.common.PagingResponse;
import com.payflow.payflow.repository.admin.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;


    @Transactional(readOnly = true)
    public DashboardMetricResponse collectMetrics() {
        return adminRepository.getDashboardMetrics();
    }

    @Transactional(readOnly = true)
    public PagingResponse<OrderSummaryResponse> searchOrders(OrderPageRequest orderPageRequest) {
        PageImpl<OrderSummaryResponse> orderSummaryPage =  adminRepository.getOrderPage(orderPageRequest);
        return new PagingResponse<>(orderSummaryPage);
    }

    @Transactional(readOnly = true)
    public SettlementOverviewResponse getSettlementOverview(LocalDateTime start, LocalDateTime end) {
        return adminRepository.getSettlementOverview(start, end);
    }

    public Page<SettlementResponse> getSettlement(LocalDateTime start, LocalDateTime end, Pageable pageable) {
        return adminRepository.getSettlement(start,end,pageable);
    }
}
