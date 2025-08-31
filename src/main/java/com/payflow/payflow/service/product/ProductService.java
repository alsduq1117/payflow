package com.payflow.payflow.service.product;

import com.payflow.payflow.domain.auth.User;
import com.payflow.payflow.domain.product.Product;
import com.payflow.payflow.dto.common.PagingResponse;
import com.payflow.payflow.dto.product.*;
import com.payflow.payflow.exception.auth.UserNotFoundException;
import com.payflow.payflow.exception.product.ProductNotFound;
import com.payflow.payflow.repository.auth.UserRepository;
import com.payflow.payflow.repository.product.ProductRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Transactional
    public ProductResponse create(CreteaProductRequest creteaProductRequest, Long userId) {

        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        Product product = Product.builder()
                .name(creteaProductRequest.getName())
                .description(creteaProductRequest.getDescription())
                .price(creteaProductRequest.getPrice())
                .fileUrl(creteaProductRequest.getFileUrl())
                .thumbnailUrl(creteaProductRequest.getThumbnailUrl())
                .sellerId(userId)
                .build();

        productRepository.save(product);

        return ProductResponse.from(product, user.getNickname());
    }


    public ProductResponse get(Long productId) {
        return productRepository.findByIdWithSellerNickname(productId).orElseThrow(ProductNotFound::new);
    }

    public PagingResponse<ProductResponse> getList(GetProductsRequest getProductsRequest) {
        PageImpl<ProductResponse> productPage = productRepository.getProductPage(getProductsRequest);
        return new PagingResponse<>(productPage);
    }

    @Transactional
    public ProductResponse edit(Long productId, @Valid EditProductRequest editProductRequest, Long userId) {

        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Product product = productRepository.findById(productId).orElseThrow(ProductNotFound::new);

        ProductEditor.ProductEditorBuilder productEditorBuilder = product.toEditor();

        ProductEditor productEditor = productEditorBuilder
                .name(editProductRequest.getName())
                .price(editProductRequest.getPrice())
                .description(editProductRequest.getDescription())
                .price(editProductRequest.getPrice())
                .thumbnailUrl(editProductRequest.getThumbnailUrl())
                .build();

        product.edit(productEditor);

        return ProductResponse.from(product, user.getNickname());
    }

    @Transactional
    public void delete(Long productId, Long userId) {
        userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Product product = productRepository.findById(productId).orElseThrow(ProductNotFound::new);
        productRepository.delete(product);
    }
}
