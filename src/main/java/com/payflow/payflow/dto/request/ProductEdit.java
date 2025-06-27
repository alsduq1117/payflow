package com.payflow.payflow.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductEdit {

    private String productName;

    private Long price;

    private String fileUrl;

    private String thumbnailUrl;

    private String description;

}
