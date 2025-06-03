package org.bookStore.common.events;

import org.bookStore.common.dto.CreateOrderDetailsRequest;

import java.util.List;

public record ShippingOrderCreatedEvent(
        String orderId,
        Long userId,
        Long shippingOrderId,
        List<CreateOrderDetailsRequest> orderDetails
) {}