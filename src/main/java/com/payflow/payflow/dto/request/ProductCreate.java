package com.payflow.payflow.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductCreate {

    @NotBlank
    @Size(min = 1, max = 100)
    private String name;

    @NotNull
    @Positive
    private Integer price;

    @NotBlank
    private String fileUrl;

    @NotBlank
    private String thumbnailUrl;

    private String description;

}
