package org.bookStore.composition.addCartItem.cart;

import org.bookStore.composition.addCartItem.cartItem.CartItemResponse;

import java.util.List;

public record CartResponse(
        Long id,
        Long userId,
        List<CartItemResponse> cartItems
) {
}
