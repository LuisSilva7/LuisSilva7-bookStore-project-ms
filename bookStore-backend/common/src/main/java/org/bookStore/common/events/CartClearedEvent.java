package org.bookStore.common.events;

public record CartClearedEvent(
        String orderId,
        Long userId
) {
}

