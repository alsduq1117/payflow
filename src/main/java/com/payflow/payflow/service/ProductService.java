package com.payflow.payflow.service;

import com.payflow.payflow.dto.request.ProductCreate;
import com.payflow.payflow.dto.request.ProductEdit;
import com.payflow.payflow.dto.request.ProductPageRequest;
import com.payflow.payflow.dto.response.PagingResponse;
import com.payflow.payflow.dto.response.ProductResponse;
import com.payflow.payflow.domain.Product;
import com.payflow.payflow.dto.request.ProductEditor;
import com.payflow.payflow.exception.ProductNotFound;
import com.payflow.payflow.repository.ProductRepository;
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
                .build();// task : UserPricipal 로 부터 sellerId 받아오기

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
