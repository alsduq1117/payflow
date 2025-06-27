package com.payflow.payflow.repository;

import com.payflow.payflow.entity.Product;
import com.payflow.payflow.dto.request.ProductPageRequest;
import org.springframework.data.domain.Page;

public interface ProductCustomRepository {

    Page<Product> getProductPage(ProductPageRequest productPageRequest);
}
