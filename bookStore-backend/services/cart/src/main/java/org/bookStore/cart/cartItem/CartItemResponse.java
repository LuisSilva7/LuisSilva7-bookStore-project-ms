package org.bookStore.cart.cartItem;

public record CartItemResponse(
        Long id,
        int quantity,
        Double unitPrice,
        Double subTotal,
        Long bookId
) {}