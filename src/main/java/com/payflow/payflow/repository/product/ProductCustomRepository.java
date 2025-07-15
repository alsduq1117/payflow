package com.payflow.payflow.repository.product;

import com.payflow.payflow.domain.product.Product;
import com.payflow.payflow.dto.product.ProductPageRequest;
import org.springframework.data.domain.Page;

public interface ProductCustomRepository {

    Page<Product> getProductPage(ProductPageRequest productPageRequest);
}
