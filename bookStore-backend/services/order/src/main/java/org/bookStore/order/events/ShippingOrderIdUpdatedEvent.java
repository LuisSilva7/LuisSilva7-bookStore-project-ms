package org.bookStore.order.events;

import org.bookStore.common.utils.CreateOrderDetailsRequest;

import java.util.List;

public record ShippingOrderIdUpdatedEvent(
        String orderId,
        Long userId,
        Long shippingOrderId,
        List<CreateOrderDetailsRequest> orderDetails
) {}
