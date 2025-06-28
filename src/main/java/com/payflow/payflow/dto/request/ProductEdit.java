package com.payflow.payflow.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductEdit {

    private String name;

    private Integer price;

    private String fileUrl;

    private String thumbnailUrl;

    private String description;

}
