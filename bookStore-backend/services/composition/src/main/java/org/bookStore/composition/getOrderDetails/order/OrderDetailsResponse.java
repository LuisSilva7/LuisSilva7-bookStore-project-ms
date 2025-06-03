package org.bookStore.composition.getOrderDetails.order;

public record OrderDetailsResponse(
        Long id,
        int quantity,
        double unitPrice,
        double subTotal,
        Long bookId
) {}
