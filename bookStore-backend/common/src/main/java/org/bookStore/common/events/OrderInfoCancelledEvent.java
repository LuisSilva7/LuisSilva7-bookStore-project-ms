package org.bookStore.common.events;

import org.bookStore.common.utils.OrderStatus;

public record OrderInfoCancelledEvent(
        String orderId,
        OrderStatus status
) {
}
