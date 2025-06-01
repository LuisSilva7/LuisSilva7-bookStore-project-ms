package org.bookStore.order.command.events;

public record CartClearedEvent(
        String orderId,
        String userId
) {
}