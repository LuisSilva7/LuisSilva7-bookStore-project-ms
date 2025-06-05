package org.bookStore.order.events;

public record OrderCancelledEvent(
        String orderId
) {
}
