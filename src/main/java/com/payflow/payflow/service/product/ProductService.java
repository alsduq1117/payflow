package com.payflow.payflow.service.product;

import com.payflow.payflow.dto.product.ProductCreate;
import com.payflow.payflow.dto.product.ProductEdit;
import com.payflow.payflow.dto.product.ProductPageRequest;
import com.payflow.payflow.dto.common.PagingResponse;
import com.payflow.payflow.dto.product.ProductResponse;
import com.payflow.payflow.domain.product.Product;
import com.payflow.payflow.dto.product.ProductEditor;
import com.payflow.payflow.exception.product.ProductNotFound;
import com.payflow.payflow.repository.product.ProductRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional
    public ProductResponse create(ProductCreate productCreate) {
        Product product = Product.builder()
                .name(productCreate.getName())
                .description(productCreate.getDescription())
                .price(productCreate.getPrice())
                .price(productCreate.getPrice())
                .fileUrl(productCreate.getFileUrl())
                .thumbnailUrl(productCreate.getThumbnailUrl())
                .sellerId(1L) // task : UserPricipal 로 부터 sellerId 받아오기
                .build();

        productRepository.save(product);

        return ProductResponse.from(product);
    }


    public ProductResponse get(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(RuntimeException::new);
        return ProductResponse.from(product);
    }

    public PagingResponse<ProductResponse> getList(ProductPageRequest productPageRequest) {
        Page<Product> productPage = productRepository.getProductPage(productPageRequest);
        return new PagingResponse<>(productPage, ProductResponse.class);
    }

    @Transactional
    public ProductResponse edit(Long productId, @Valid ProductEdit productEdit) {
        Product product = productRepository.findById(productId).orElseThrow(ProductNotFound::new);

        ProductEditor.ProductEditorBuilder productEditorBuilder = product.toEditor();

        ProductEditor productEditor = productEditorBuilder
                .name(productEdit.getName())
                .price(productEdit.getPrice())
                .description(productEdit.getDescription())
                .price(productEdit.getPrice())
                .thumbnailUrl(productEdit.getThumbnailUrl())
                .build();

        product.edit(productEditor);

        return ProductResponse.from(product);
    }

    @Transactional
    public void delete(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(ProductNotFound::new);
        productRepository.delete(product);
    }
}
