package org.bookStore.common.utils;

public record CreateOrderDetailsRequest(
        int quantity,
        double unitPrice,
        Long bookId
) {}