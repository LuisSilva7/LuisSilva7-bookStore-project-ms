package org.bookStore.common.events;

import org.bookStore.common.utils.OrderStatus;

import java.time.LocalDateTime;

public record OrderInfoEvent(
        String orderId,
        LocalDateTime orderDate,
        double totalPrice,
        OrderStatus status,
        Long userId
) {
}
