package org.bookStore.common.commands;

public record CancelOrderCommand(
        String orderId
) {
}