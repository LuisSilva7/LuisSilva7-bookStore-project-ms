package org.bookStore.order.command.commands;

public record FinalizeOrderCommand(
        String orderId
) {
}
