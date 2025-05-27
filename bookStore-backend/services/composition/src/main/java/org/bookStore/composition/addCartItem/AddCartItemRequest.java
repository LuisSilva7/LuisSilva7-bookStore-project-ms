package org.bookStore.composition.addCartItem;

public record AddCartItemRequest(
        int quantity,
        Double unitPrice,
        Long bookId
) {
}

