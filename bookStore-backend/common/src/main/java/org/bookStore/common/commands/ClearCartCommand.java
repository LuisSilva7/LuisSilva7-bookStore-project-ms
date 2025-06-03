package org.bookStore.common.commands;

import org.bookStore.common.utils.CreateOrderDetailsRequest;

import java.util.List;

public record ClearCartCommand(
        String orderId,
        Long userId,
        List<CreateOrderDetailsRequest> orderDetails
) {
}