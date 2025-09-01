package com.payflow.payflow.repository.auth;

import com.payflow.payflow.domain.auth.QUser;
import com.payflow.payflow.domain.payment.PaymentStatus;
import com.payflow.payflow.domain.payment.QPaymentEvent;
import com.payflow.payflow.domain.payment.QPaymentOrder;
import com.payflow.payflow.domain.product.QProduct;
import com.payflow.payflow.domain.wallet.QWallet;
import com.payflow.payflow.dto.admin.OrderSummaryResponse;
import com.payflow.payflow.dto.auth.UserWalletResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryCustom {

    public final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<OrderSummaryResponse> getMyOrders(Long userId) {
        QPaymentOrder po = QPaymentOrder.paymentOrder;
        QPaymentEvent pe = QPaymentEvent.paymentEvent;
        QProduct p = QProduct.product;
        QUser user = QUser.user;

        return jpaQueryFactory.select(Projections.fields(
                        OrderSummaryResponse.class,
                        po.id,
                        po.orderId,
                        p.id.as("productId"),
                        p.name.as("productName"),
                        p.thumbnailUrl.as("thumbnailUrl"),
                        p.fileUrl.as("fileUrl"),
                        po.amount,
                        po.status,
                        pe.paymentMethod.as("method"),
                        pe.approvedAt
                ))
                .from(po)
                .join(pe).on(po.paymentEventId.eq(pe.id))
                .join(p).on(po.productId.eq(p.id))
                .join(user).on(pe.buyerId.eq(user.id))
                .where(po.status.eq(PaymentStatus.SUCCESS), user.id.eq(userId))
                .orderBy(po.updatedAt.desc(), p.id.asc())
                .fetch();
    }

    @Override
    public UserWalletResponse getUserWallet(Long userId) {
        QWallet wl = QWallet.wallet;

        return jpaQueryFactory.select(Projections.fields(
                        UserWalletResponse.class,
                        wl.balance
                ))
                .from(wl)
                .where(wl.userId.eq(userId))
                .fetchOne();
    }
}
