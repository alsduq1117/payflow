package com.payflow.payflow.repository.admin;

import com.payflow.payflow.dto.admin.OrderPageRequest;
import com.payflow.payflow.dto.admin.OrderSummaryResponse;
import org.springframework.data.domain.PageImpl;

public interface AdminOrdersRepository {
    PageImpl<OrderSummaryResponse> getOrderPage(OrderPageRequest orderPageRequest);
}
