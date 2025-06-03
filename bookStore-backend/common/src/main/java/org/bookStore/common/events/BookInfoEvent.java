package org.bookStore.common.events;

import org.bookStore.common.utils.CreateOrderDetailsRequest;

import java.util.List;

public record BookInfoEvent(
        String orderId,
        List<CreateOrderDetailsRequest> books
) {
}
