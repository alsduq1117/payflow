package com.payflow.payflow.service.admin;

import com.payflow.payflow.dto.admin.DashboardMetricResponse;
import com.payflow.payflow.dto.admin.OrderPageRequest;
import com.payflow.payflow.dto.admin.OrderSummaryResponse;
import com.payflow.payflow.dto.common.PagingResponse;
import com.payflow.payflow.repository.admin.AdminMetricsRepository;
import com.payflow.payflow.repository.admin.AdminOrdersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminMetricsRepository adminMetricsRepository;
    private final AdminOrdersRepository adminOrdersRepository;


    @Transactional(readOnly = true)
    public DashboardMetricResponse collectMetrics() {
        return adminMetricsRepository.getDashboardMetrics();
    }

    @Transactional(readOnly = true)
    public PagingResponse<OrderSummaryResponse> searchOrders(OrderPageRequest orderPageRequest) {
        PageImpl<OrderSummaryResponse> orderSummaryPage =  adminOrdersRepository.getOrderPage(orderPageRequest);
        return new PagingResponse<>(orderSummaryPage);
    }
}
