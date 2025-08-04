package com.payflow.payflow.repository.product;

import com.payflow.payflow.dto.product.ProductPageRequest;
import com.payflow.payflow.dto.product.ProductResponse;
import org.springframework.data.domain.PageImpl;

import java.util.Optional;

public interface ProductCustomRepository {

    PageImpl<ProductResponse> getProductPage(ProductPageRequest productPageRequest);

    Optional<ProductResponse> findByIdWithSellerNickname(Long productId);
}
