package org.bookStore.common.events;

public record OrderCancelledEvent(
        String orderId
) {
}
