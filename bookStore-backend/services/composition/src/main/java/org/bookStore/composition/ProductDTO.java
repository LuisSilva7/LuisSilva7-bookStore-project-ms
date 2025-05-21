package org.bookStore.composition;

public record ProductDTO (
    Long id,
    String title,
    double price,
    int stock
) {
}
