package com.payflow.payflow.dto.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class EditProductRequest {

    @NotBlank
    @Size(min = 1, max = 100)
    private String name;

    private Long price;

    @NotBlank
    private String fileUrl;

    @NotBlank
    private String thumbnailUrl;

    private String description;

    public EditProductRequest(String name, Long price, String fileUrl, String thumbnailUrl, String description) {
        this.name = name;
        this.price = price;
        this.fileUrl = fileUrl;
        this.thumbnailUrl = thumbnailUrl;
        this.description = description;
    }
}
