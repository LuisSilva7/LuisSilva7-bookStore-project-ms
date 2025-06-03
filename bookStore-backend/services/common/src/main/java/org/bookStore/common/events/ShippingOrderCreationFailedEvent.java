package org.bookStore.common.events;

public record ShippingOrderCreationFailedEvent(
        String orderId,
        Long userId
) {
}
