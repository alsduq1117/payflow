package com.payflow.payflow.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@SQLDelete(sql = "UPDATE product SET deleted_at = NOW() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String productName;

    private Long price;

    private String fileUrl;

    private String thumbnailUrl;

    private String description;

    private Long sellerId;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Builder
    public Product(String productName, Long price, String fileUrl, String thumbnailUrl, String description, Long sellerId) {
        this.productName = productName;
        this.price = price;
        this.fileUrl = fileUrl;
        this.thumbnailUrl = thumbnailUrl;
        this.description = description;
        this.sellerId = sellerId;
    }


    public ProductEditor.ProductEditorBuilder toEditor() {

        return ProductEditor.builder()
                .productName(productName)
                .price(price)
                .fileUrl(fileUrl)
                .thumbnailUrl(thumbnailUrl)
                .description(description);
    }

    public void edit(ProductEditor productEditor) {
        this.productName = productEditor.getProductName();
        this.price = productEditor.getPrice();
        this.fileUrl = productEditor.getFileUrl();
        this.thumbnailUrl = productEditor.getThumbnailUrl();
        this.description = productEditor.getDescription();
    }
}
