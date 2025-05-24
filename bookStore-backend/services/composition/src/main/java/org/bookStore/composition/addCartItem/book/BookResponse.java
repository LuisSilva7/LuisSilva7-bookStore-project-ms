package org.bookStore.composition.addCartItem.book;

public record BookResponse (
        Long id,
        String title,
        double price,
        int stock
) {
}
