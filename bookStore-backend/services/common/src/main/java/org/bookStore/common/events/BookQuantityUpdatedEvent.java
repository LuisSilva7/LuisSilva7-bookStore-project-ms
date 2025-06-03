package org.bookStore.common.events;

import org.bookStore.common.utils.CreateOrderDetailsRequest;

import java.util.List;

public record BookQuantityUpdatedEvent(
        String orderId,
        Long userId,
        List<CreateOrderDetailsRequest> orderDetails
) {
}