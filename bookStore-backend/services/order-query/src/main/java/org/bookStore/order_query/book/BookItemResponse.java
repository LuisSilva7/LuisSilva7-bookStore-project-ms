package org.bookStore.order_query.book;

public record BookItemResponse(
        Long bookId,
        Double unitPrice,
        Integer quantity
) {
}
