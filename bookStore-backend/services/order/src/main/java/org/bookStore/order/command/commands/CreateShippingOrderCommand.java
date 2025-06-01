package org.bookStore.order.command.commands;

public record CreateShippingOrderCommand(
        String orderId,
        String userId
) {
}