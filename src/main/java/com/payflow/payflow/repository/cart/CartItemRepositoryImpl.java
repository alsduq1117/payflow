package com.payflow.payflow.repository.cart;

import com.payflow.payflow.domain.cart.QCartItem;
import com.payflow.payflow.domain.payment.PaymentOrder;
import com.payflow.payflow.domain.payment.QPaymentEvent;
import com.payflow.payflow.domain.payment.QPaymentOrder;
import com.payflow.payflow.domain.product.QProduct;
import com.payflow.payflow.dto.cart.CartItemResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class CartItemRepositoryImpl implements CartItemCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<CartItemResponse> findCartItemsByCartId(Long cartId) {
        QCartItem ci = QCartItem.cartItem;
        QProduct pd = QProduct.product;

        return jpaQueryFactory.select(Projections.constructor(
                        CartItemResponse.class,
                        ci.cart.id,
                        pd.id,
                        pd.name,
                        pd.price,
                        pd.thumbnailUrl))
                .from(ci)
                .join(ci.product, pd)
                .where(ci.cart.id.eq(cartId))
                .fetch();
    }

    @Override
    public void removePurchasedItems(String orderId) {
        QPaymentOrder po = QPaymentOrder.paymentOrder;
        QPaymentEvent pe = QPaymentEvent.paymentEvent;
        QCartItem ci = QCartItem.cartItem;

        Long buyerId = jpaQueryFactory.select(pe.buyerId)
                .from(pe)
                .join(po).on(po.paymentEventId.eq(pe.id))
                .where(po.orderId.eq(orderId))
                .fetchOne();

        List<Long> productIds = jpaQueryFactory.select(po.productId)
                .from(po)
                .where(po.orderId.eq(orderId))
                .fetch();

        jpaQueryFactory.delete(ci)
                .where(ci.cart.userId.eq(buyerId).and(ci.product.id.in(productIds)))
                .execute();

    }

}
