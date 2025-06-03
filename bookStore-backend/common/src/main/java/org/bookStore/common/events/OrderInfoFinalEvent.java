package org.bookStore.common.events;

import org.bookStore.common.utils.OrderStatus;

public record OrderInfoFinalEvent(
        String orderId,
        OrderStatus status
) {
}
