package org.bookStore.order.orderDetails;

public record OrderDetailsResponse(
        Long id,
        int quantity,
        double unitPrice,
        double subTotal,
        Long bookId
) {}
