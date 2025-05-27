package org.bookStore.composition.addCartItem.cart;

import org.bookStore.composition.addCartItem.cartItem.CartItemResponse;

import java.util.List;

public record CartResponse(
        List<CartItemResponse> cartItems
) {
}
