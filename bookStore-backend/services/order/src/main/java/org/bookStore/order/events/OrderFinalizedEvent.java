package org.bookStore.order.events;

public record OrderFinalizedEvent(
        String orderId
) {
}
