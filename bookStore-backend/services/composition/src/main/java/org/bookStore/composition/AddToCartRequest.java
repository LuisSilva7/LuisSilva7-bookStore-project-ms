package org.bookStore.composition;

public record AddToCartRequest(
        Long userId,
        Long bookId,
        int quantity
) {
}

