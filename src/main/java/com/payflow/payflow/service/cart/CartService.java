package com.payflow.payflow.service.cart;

import com.payflow.payflow.domain.auth.User;
import com.payflow.payflow.domain.cart.Cart;
import com.payflow.payflow.domain.cart.CartItem;
import com.payflow.payflow.domain.payment.PaymentConfirmationSuccessEvent;
import com.payflow.payflow.domain.product.Product;
import com.payflow.payflow.dto.cart.CartItemResponse;
import com.payflow.payflow.exception.auth.UserNotFoundException;
import com.payflow.payflow.exception.cart.AlreadyAddedInCartException;
import com.payflow.payflow.exception.cart.CartNotFoundException;
import com.payflow.payflow.exception.product.ProductNotFound;
import com.payflow.payflow.repository.auth.UserRepository;
import com.payflow.payflow.repository.cart.CartItemRepository;
import com.payflow.payflow.repository.cart.CartRepository;
import com.payflow.payflow.repository.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    @Transactional
    public void addItemsToCart(Long userId, Long productId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Cart cart = cartRepository.findByUserId(user.getId()).orElseThrow(CartNotFoundException::new);
        Product product = productRepository.findById(productId).orElseThrow(ProductNotFound::new);
        cartItemRepository.findCartItemByProduct(product).ifPresent(cartItem -> {throw new AlreadyAddedInCartException();});
        cartItemRepository.save(CartItem.builder()
                .cart(cart)
                .product(product)
                .build());
    }

    public List<CartItemResponse> getItemsFromCart(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Cart cart = cartRepository.findByUserId(user.getId()).orElseThrow(CartNotFoundException::new);
        return cartItemRepository.findCartItemsByCartId(cart.getId());
    }

    @Transactional
    public void deleteItemsFromCart(Long userId, List<Long> productIds) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Cart cart = cartRepository.findByUserId(user.getId()).orElseThrow(CartNotFoundException::new);
        cartItemRepository.deleteByCartIdAndProductIds(cart.getId(), productIds);
    }

    @Transactional
    public void removePurchasedItems(PaymentConfirmationSuccessEvent event) {
        cartItemRepository.removePurchasedItems(event.getOrderId());


    }
}
