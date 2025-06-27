package com.payflow.payflow.dto.response;

import com.payflow.payflow.entity.Product;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductResponse {

    private final Long id;

    private final String productName;

    private final Long price;

    private final String fileUrl;

    private final String thumbnailUrl;

    private final String description;

    private final Long sellerId;

    @Builder
    public ProductResponse(Long id, String productName, Long price, String fileUrl, String thumbnailUrl, String description, Long sellerId) {
        this.id = id;
        this.productName = productName;
        this.price = price;
        this.fileUrl = fileUrl;
        this.thumbnailUrl = thumbnailUrl;
        this.description = description;
        this.sellerId = sellerId;
    }

    public static ProductResponse from(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .productName(product.getProductName())
                .price(product.getPrice())
                .fileUrl(product.getFileUrl())
                .thumbnailUrl(product.getThumbnailUrl())
                .description(product.getDescription())
                .sellerId(product.getSellerId())
                .build();
    }
}