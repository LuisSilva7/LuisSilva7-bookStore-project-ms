package org.bookStore.composition.addCartItem;

public record AddCartItemRequest(
        Long userId,
        Long bookId,
        int quantity
) {
}

