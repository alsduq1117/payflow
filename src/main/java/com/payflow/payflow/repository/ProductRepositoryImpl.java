package com.payflow.payflow.repository;

import com.payflow.payflow.entity.Product;
import com.payflow.payflow.domain.QProduct;
import com.payflow.payflow.dto.request.ProductPageRequest;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductCustomRepository{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<Product> getProductPage(ProductPageRequest productPageRequest) {
        long totalCount = jpaQueryFactory
                .select(QProduct.product.count())
                .from(QProduct.product)
                .fetchFirst();

        List<Product> productList = jpaQueryFactory.selectFrom(QProduct.product)
                .limit(productPageRequest.getSize())
                .offset(productPageRequest.getOffset())
                .orderBy(QProduct.product.id.desc())
                .fetch();
        return new PageImpl<>(productList, productPageRequest.getPageable() ,totalCount);
    }
}
