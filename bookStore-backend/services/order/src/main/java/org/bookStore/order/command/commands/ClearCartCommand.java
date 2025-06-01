package org.bookStore.order.command.commands;

public record ClearCartCommand(
        String orderId,
        String userId
) {
}