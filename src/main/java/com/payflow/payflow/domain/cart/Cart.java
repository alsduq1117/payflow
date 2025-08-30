package com.payflow.payflow.domain.cart;

import com.payflow.payflow.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "carts")
public class Cart extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Builder
    public Cart(Long userId) {
        this.userId = userId;
    }

    public static Cart createFor(Long userId) {
        return Cart.builder()
                .userId(userId)
                .build();
    }
}
