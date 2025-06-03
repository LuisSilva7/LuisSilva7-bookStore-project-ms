package org.bookStore.composition.common.book;

public record BookResponse (
        Long id,
        String title,
        double price,
        int quantity
) {
}
