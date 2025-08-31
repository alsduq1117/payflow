package com.payflow.payflow.repository.cart;

import com.payflow.payflow.domain.cart.Cart;
import com.payflow.payflow.domain.cart.CartItem;
import com.payflow.payflow.domain.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long>, CartItemCustomRepository {

    @Modifying
    @Query("DELETE FROM CartItem ci WHERE ci.cart.id = :cartId AND ci.product.id IN :productIds")
    void deleteByCartIdAndProductIds(@Param("cartId") Long cartId, @Param("productIds") List<Long> productIds);

    Optional<CartItem> findCartItemByProduct(Product product);

    List<CartItem> findCartItemByCart(Cart cart);
}
