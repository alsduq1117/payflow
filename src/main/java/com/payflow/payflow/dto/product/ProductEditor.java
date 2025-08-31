package com.payflow.payflow.dto.product;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductEditor {

    private String name;

    private Long price;

    private String fileUrl;

    private String thumbnailUrl;

    private String description;

    @Builder
    public ProductEditor(String name, Long price, String fileUrl, String thumbnailUrl, String description) {
        this.name = name;
        this.price = price;
        this.fileUrl = fileUrl;
        this.thumbnailUrl = thumbnailUrl;
        this.description = description;
    }
}
