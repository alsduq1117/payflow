package com.payflow.payflow.dto.product;

import com.payflow.payflow.domain.product.Product;
import lombok.Getter;

@Getter
public class ProductResponse {

    private final Long id;

    private final String name;

    private final Long price;

    private final String fileUrl;

    private final String thumbnailUrl;

    private final String description;

    private final Long sellerId;

    private final String sellerNickname;

    public ProductResponse(Long id, String name, Long price, String fileUrl, String thumbnailUrl, String description, Long sellerId, String sellerNickname) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.fileUrl = fileUrl;
        this.thumbnailUrl = thumbnailUrl;
        this.description = description;
        this.sellerId = sellerId;
        this.sellerNickname = sellerNickname;
    }

    public static ProductResponse from(Product product, String sellerNickname) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getFileUrl(),
                product.getThumbnailUrl(),
                product.getDescription(),
                product.getSellerId(),
                sellerNickname
        );
    }
}