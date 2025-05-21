package org.bookStore.composition;

public record CartItemDTO (
    Long bookId,
    int quantity,

    String title,
    double price,
    double total
) {
}

