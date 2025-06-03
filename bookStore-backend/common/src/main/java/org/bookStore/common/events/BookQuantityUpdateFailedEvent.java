package org.bookStore.common.events;

public record BookQuantityUpdateFailedEvent(
        String orderId,
        Long userId
) {
}
