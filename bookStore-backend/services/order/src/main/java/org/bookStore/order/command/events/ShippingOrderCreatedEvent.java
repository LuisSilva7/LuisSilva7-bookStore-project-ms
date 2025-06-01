package org.bookStore.order.command.events;

public record ShippingOrderCreatedEvent(
        String orderId,
        String userId
) {
}