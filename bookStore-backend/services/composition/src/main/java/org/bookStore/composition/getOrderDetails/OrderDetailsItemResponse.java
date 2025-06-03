package org.bookStore.composition.getOrderDetails;

public record OrderDetailsItemResponse(
        Long id,
        Long bookId,
        Double unitPrice,
        Integer quantity
) {
}
