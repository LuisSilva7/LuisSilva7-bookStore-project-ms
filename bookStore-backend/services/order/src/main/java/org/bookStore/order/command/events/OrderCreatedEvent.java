package org.bookStore.order.command.events;

public record OrderCreatedEvent(
        String orderId,
        String userId
) {
}