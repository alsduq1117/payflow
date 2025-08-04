package com.payflow.payflow.service.product;

import com.payflow.payflow.domain.auth.User;
import com.payflow.payflow.dto.product.ProductCreate;
import com.payflow.payflow.dto.product.ProductEdit;
import com.payflow.payflow.dto.product.ProductPageRequest;
import com.payflow.payflow.dto.common.PagingResponse;
import com.payflow.payflow.dto.product.ProductResponse;
import com.payflow.payflow.domain.product.Product;
import com.payflow.payflow.dto.product.ProductEditor;
import com.payflow.payflow.exception.auth.UserNotFoundException;
import com.payflow.payflow.exception.product.ProductNotFound;
import com.payflow.payflow.repository.auth.UserRepository;
import com.payflow.payflow.repository.product.ProductRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
    public ProductResponse create(ProductCreate productCreate, Long userId) {

        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        Product product = Product.builder()
                .name(productCreate.getName())
                .description(productCreate.getDescription())
                .price(productCreate.getPrice())
                .fileUrl(productCreate.getFileUrl())
                .thumbnailUrl(productCreate.getThumbnailUrl())
                .sellerId(userId)
                .build();

        productRepository.save(product);

        return ProductResponse.from(product, user.getNickname());
    }


    public ProductResponse get(Long productId) {
        return productRepository.findByIdWithSellerNickname(productId).orElseThrow(ProductNotFound::new);
    }

    public PagingResponse<ProductResponse> getList(ProductPageRequest productPageRequest) {
        PageImpl<ProductResponse> productPage = productRepository.getProductPage(productPageRequest);
        return new PagingResponse<>(productPage);
    }

    @Transactional
    public ProductResponse edit(Long productId, @Valid ProductEdit productEdit, Long userId) {

        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
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

        return ProductResponse.from(product, user.getNickname());
    }

    @Transactional
    public void delete(Long productId, Long userId) {
        userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Product product = productRepository.findById(productId).orElseThrow(ProductNotFound::new);
        productRepository.delete(product);
    }
}
