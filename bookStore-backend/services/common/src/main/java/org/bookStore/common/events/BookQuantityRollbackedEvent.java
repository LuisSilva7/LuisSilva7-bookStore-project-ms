package org.bookStore.common.events;

import org.bookStore.common.dto.CreateOrderDetailsRequest;

import java.util.List;

public record BookQuantityRollbackedEvent(
        String orderId,
        Long userId,
        List<CreateOrderDetailsRequest> books
) {
}
