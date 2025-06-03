package org.bookStore.common.dto;

public record CreateOrderDetailsRequest(
        int quantity,
        double unitPrice,
        Long bookId
) {}