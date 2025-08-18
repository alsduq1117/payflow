package com.payflow.payflow.controller.admin;

import com.payflow.payflow.dto.admin.*;
import com.payflow.payflow.dto.common.PagingResponse;
import com.payflow.payflow.service.admin.AdminService;
import com.payflow.payflow.util.DateRangeUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final AdminService adminService;


    @GetMapping("/metrics")
    public ResponseEntity<DashboardMetricResponse> getMetrics() {
        return ResponseEntity.ok(adminService.collectMetrics());
    }

    @GetMapping("/orders")
    public ResponseEntity<PagingResponse<OrderSummaryResponse>> searchOrders(@ModelAttribute OrderPageRequest orderPageRequest) {
        PagingResponse<OrderSummaryResponse> orderSummaryList = adminService.searchOrders(orderPageRequest);
        return ResponseEntity.ok(orderSummaryList);
    }

    @GetMapping("/settlements/overview")
    public ResponseEntity<SettlementOverviewResponse> overview(@ModelAttribute SettlementOverviewRequest settlementOverviewRequest) {
        DateRangeUtil.DateRange dateRange = DateRangeUtil.of(settlementOverviewRequest.getFrom(), settlementOverviewRequest.getTo());
        return ResponseEntity.ok(adminService.getSettlementOverview(dateRange.getStart(), dateRange.getEnd()));
    }

    @GetMapping("/settlements")
    public ResponseEntity<Page<SettlementResponse>> bySeller(@ModelAttribute SettlementRequest settlementRequest, Pageable pageable) {
        DateRangeUtil.DateRange dateRange = DateRangeUtil.of(settlementRequest.getFrom(), settlementRequest.getTo());
        return ResponseEntity.ok(adminService.getSettlement(dateRange.getStart(), dateRange.getEnd(), pageable));
    }


}
