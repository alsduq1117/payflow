package com.payflow.payflow.repository.admin;

import com.payflow.payflow.dto.admin.SettlementOverviewResponse;
import com.payflow.payflow.dto.admin.SettlementResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface AdminSettlementRepository {
    SettlementOverviewResponse getSettlementOverview(LocalDateTime start, LocalDateTime end);

    Page<SettlementResponse> getSettlement(LocalDateTime start, LocalDateTime end, Pageable pageable);
}
