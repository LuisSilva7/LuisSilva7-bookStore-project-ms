package org.bookStore.composition.addCartItem.cart;

import java.util.List;

public record CartResponse(
        Long userId,
        List<CartItemDTO> items
) {
}
