package org.bookStore.order.command.events;

public record BookQuantityUpdatedEvent(
        String orderId,
        String userId
) {
}