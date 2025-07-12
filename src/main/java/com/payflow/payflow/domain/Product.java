package com.payflow.payflow.domain;

import com.payflow.payflow.domain.common.BaseEntity;
import com.payflow.payflow.dto.request.ProductEditor;
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
@Table(name = "products")
@SQLDelete(sql = "UPDATE product SET deleted_at = NOW() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer price;

    @Column(name = "file_url")
    private String fileUrl;

    @Column(name = "thumbnail_url")
    private String thumbnailUrl;

    private String description;

    @Column(name = "seller_id")
    private Long sellerId;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Builder
    public Product(String name, Integer price, String fileUrl, String thumbnailUrl, String description, Long sellerId) {
        this.name = name;
        this.price = price;
        this.fileUrl = fileUrl;
        this.thumbnailUrl = thumbnailUrl;
        this.description = description;
        this.sellerId = sellerId;
    }


    public ProductEditor.ProductEditorBuilder toEditor() {

        return ProductEditor.builder()
                .name(name)
                .price(price)
                .fileUrl(fileUrl)
                .thumbnailUrl(thumbnailUrl)
                .description(description);
    }

    public void edit(ProductEditor productEditor) {
        this.name = productEditor.getName();
        this.price = productEditor.getPrice();
        this.fileUrl = productEditor.getFileUrl();
        this.thumbnailUrl = productEditor.getThumbnailUrl();
        this.description = productEditor.getDescription();
    }
}
