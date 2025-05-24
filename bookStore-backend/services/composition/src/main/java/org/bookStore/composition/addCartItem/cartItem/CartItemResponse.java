package org.bookStore.composition.addCartItem.cartItem;

public record CartItemResponse(
        Long bookId,
        int quantity,

        String title,
        double price,
        double total
) {
}
