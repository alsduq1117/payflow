package com.payflow.payflow.dto.response;

import com.payflow.payflow.domain.Product;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductResponse {

    private final Long id;

    private final String name;

    private final Integer price;

    private final String fileUrl;

    private final String thumbnailUrl;

    private final String description;

    private final Long sellerId;

    @Builder
    public ProductResponse(Long id, String name, Integer price, String fileUrl, String thumbnailUrl, String description, Long sellerId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.fileUrl = fileUrl;
        this.thumbnailUrl = thumbnailUrl;
        this.description = description;
        this.sellerId = sellerId;
    }

    public static ProductResponse from(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .fileUrl(product.getFileUrl())
                .thumbnailUrl(product.getThumbnailUrl())
                .description(product.getDescription())
                .sellerId(product.getSellerId())
                .build();
    }
}