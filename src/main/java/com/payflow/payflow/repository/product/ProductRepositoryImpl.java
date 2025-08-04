package com.payflow.payflow.repository.product;

import com.payflow.payflow.domain.auth.QUser;
import com.payflow.payflow.domain.product.QProduct;
import com.payflow.payflow.dto.product.ProductPageRequest;
import com.payflow.payflow.dto.product.ProductResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public PageImpl<ProductResponse> getProductPage(ProductPageRequest request) {
        QProduct product = QProduct.product;
        QUser user = QUser.user;

        long totalCount = jpaQueryFactory
                .select(product.count())
                .from(product)
                .fetchFirst();

        List<ProductResponse> result = jpaQueryFactory
                .select(Projections.constructor(
                        ProductResponse.class,
                        product.id,
                        product.name,
                        product.price,
                        product.fileUrl,
                        product.thumbnailUrl,
                        product.description,
                        product.sellerId,
                        user.nickname
                ))
                .from(product)
                .join(user).on(product.sellerId.eq(user.id))
                .limit(request.getSize())
                .offset(request.getOffset())
                .orderBy(product.id.desc())
                .fetch();

        return new PageImpl<>(result, request.getPageable(), totalCount);
    }

    @Override
    public Optional<ProductResponse> findByIdWithSellerNickname(Long productId) {
        QProduct product = QProduct.product;
        QUser user = QUser.user;

        ProductResponse result = jpaQueryFactory
                .select(Projections.constructor(
                        ProductResponse.class,
                        product.id,
                        product.name,
                        product.price,
                        product.fileUrl,
                        product.thumbnailUrl,
                        product.description,
                        product.sellerId,
                        user.nickname
                ))
                .from(product)
                .join(user).on(product.sellerId.eq(user.id))
                .where(product.id.eq(productId))
                .fetchOne();


        return Optional.ofNullable(result);
    }
}
