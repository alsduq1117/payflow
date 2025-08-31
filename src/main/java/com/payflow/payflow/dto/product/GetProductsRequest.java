package com.payflow.payflow.dto.product;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Getter
@Setter
@Builder
public class GetProductsRequest {

    private static final int MAX_PAGE = 100;
    private static final int MIN_SIZE = 200;

    @Builder.Default
    private int page = 1;

    @Builder.Default
    private int size = 8;


    public GetProductsRequest(int page, int size) {
        this.page = page - 1;
        this.size = size;
    }

    public long getOffset() {
        return (long) (page - 1) * Math.min(size, MAX_PAGE);
    }

    public Pageable getPageable() {
        return PageRequest.of(page - 1, size);
    }
}
