package org.bookStore.order.command.events;

public record OrderFinalizedEvent(
        String orderId
) {
}
