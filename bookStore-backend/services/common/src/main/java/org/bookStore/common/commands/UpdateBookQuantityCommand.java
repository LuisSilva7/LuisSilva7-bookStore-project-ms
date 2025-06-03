package org.bookStore.common.commands;

import org.bookStore.common.dto.CreateOrderDetailsRequest;

import java.util.List;

public record UpdateBookQuantityCommand(
        String orderId,
        Long userId,
        List<CreateOrderDetailsRequest> orderDetails
) {
}