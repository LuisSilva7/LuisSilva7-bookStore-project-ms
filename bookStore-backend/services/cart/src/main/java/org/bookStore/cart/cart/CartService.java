package org.bookStore.cart.cart;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.bookStore.cart.exception.custom.CartNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CartMapper cartMapper;

    public CartResponse createCart(CreateCartRequest request) {
        Cart created = cartRepository.save(cartMapper.toCart(request));

        return cartMapper.toCartResponse(created);
    }

    public List<CartResponse> getAllCart() {
        List<Cart> carts = cartRepository.findAll();

        return carts.stream()
                .map(cartMapper::toCartResponse)
                .toList();
    }

    public CartResponse getCartIDByUserId(Long userId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new CartNotFoundException("Cart not found with userId: " + userId));

        return cartMapper.toCartResponse(cart);
    }

    @Transactional
    public CartResponse clearCart(Long userId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new CartNotFoundException("Cart not found with userId: " + userId));

        cart.getCartItems().clear();
        cartRepository.save(cart);

        return cartMapper.toCartResponse(cart);
    }
}
