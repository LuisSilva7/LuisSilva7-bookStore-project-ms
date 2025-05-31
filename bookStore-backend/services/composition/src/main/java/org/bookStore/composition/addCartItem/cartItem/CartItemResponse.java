package org.bookStore.composition.addCartItem.cartItem;

public record CartItemResponse(
        Long id,
        int quantity,
        Double unitPrice,
        Double subTotal,
        Long bookId,
        String title
) {}
