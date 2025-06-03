package org.bookStore.order.events;

public record ShippingOrderIdUpdatedEvent(
        String orderId,
        Long shippingOrderId
) {}
