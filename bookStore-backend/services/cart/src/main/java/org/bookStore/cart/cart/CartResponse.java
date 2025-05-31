package org.bookStore.cart.cart;

import org.bookStore.cart.cartItem.CartItemResponse;

import java.util.List;

public record CartResponse(
        Long id,
        Long userId,
        List<CartItemResponse> cartItems
) {
}
