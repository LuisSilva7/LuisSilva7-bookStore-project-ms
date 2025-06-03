package org.bookStore.order.command.events;

public record ShippingOrderIdUpdatedEvent(
        String orderId,
        Long shippingOrderId
) {}
