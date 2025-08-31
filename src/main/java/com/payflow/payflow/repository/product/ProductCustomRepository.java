package com.payflow.payflow.repository.product;

import com.payflow.payflow.dto.product.GetProductsRequest;
import com.payflow.payflow.dto.product.ProductResponse;
import org.springframework.data.domain.PageImpl;

import java.util.Optional;

public interface ProductCustomRepository {

    PageImpl<ProductResponse> getProductPage(GetProductsRequest getProductsRequest);

    Optional<ProductResponse> findByIdWithSellerNickname(Long productId);
}
