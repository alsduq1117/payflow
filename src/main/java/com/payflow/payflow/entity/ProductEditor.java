package com.payflow.payflow.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductEditor {

    private String productName;

    private Long price;

    private String fileUrl;

    private String thumbnailUrl;

    private String description;

    @Builder
    public ProductEditor(String productName, Long price, String fileUrl, String thumbnailUrl, String description, Long sellerId) {
        this.productName = productName;
        this.price = price;
        this.fileUrl = fileUrl;
        this.thumbnailUrl = thumbnailUrl;
        this.description = description;
    }
}
