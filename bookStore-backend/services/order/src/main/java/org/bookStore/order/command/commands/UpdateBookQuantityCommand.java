package org.bookStore.order.command.commands;

public record UpdateBookQuantityCommand(
        String orderId,
        String userId
) {
}