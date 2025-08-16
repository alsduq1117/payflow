package com.payflow.payflow.dto.admin;

import com.payflow.payflow.domain.payment.PaymentMethod;
import com.payflow.payflow.domain.payment.PaymentStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static java.lang.Math.min;

@Getter
@Setter
@Builder
public class OrderPageRequest {

    private static final int MAX_PAGE = 50;
    private static final int MAX_SIZE = 100;

    private String buyerNickname;

    private PaymentStatus status;

    private PaymentMethod method;

    private Long minAmount;

    private Long maxAmount;

    @Builder.Default
    private int page = 1;

    @Builder.Default
    private int size = 20;

    private List<String> sort; // 바인딩: 컨트롤러에서 @ModelAttribute 받으면 자동 매핑

    public void setPage(Integer page) {
        this.page = page <= 0 ? 1 : min(page, MAX_PAGE);
    }

    public long getOffset() {
        return (long) (page - 1) * min(size, MAX_SIZE);
    }

    public Sort toSort() {
        if (sort == null || sort.isEmpty()) {
            return Sort.by(Sort.Order.desc("id")); // 기본 정렬
        }
        List<Sort.Order> orders = new ArrayList<>();
        for (String raw : sort) {
            if (raw == null || raw.isBlank()) continue;
            String[] parts = raw.split(",", 2);
            String prop = parts[0].trim();
            String dir  = parts.length > 1 ? parts[1].trim() : "desc";

            // 화이트리스트 (허용 컬럼만 통과)
            if (!Set.of("id","buyerNickname","amount","status","method","approvedAt","createdAt").contains(prop)) {
                continue;
            }
            Sort.Direction direction = "asc".equalsIgnoreCase(dir) ? Sort.Direction.ASC : Sort.Direction.DESC;
            orders.add(new Sort.Order(direction, prop));
        }
        if (orders.isEmpty()) {
            orders.add(new Sort.Order(Sort.Direction.DESC, "id"));
        }
        boolean hasId = orders.stream().anyMatch(o -> o.getProperty().equals("id"));
        if (!hasId) orders.add(new Sort.Order(Sort.Direction.DESC, "id"));
        return Sort.by(orders);
    }



    public Pageable getPageable() {
        return PageRequest.of(page - 1, size);
    }
}
