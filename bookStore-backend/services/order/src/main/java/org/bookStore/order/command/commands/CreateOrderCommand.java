package org.bookStore.order.command.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public record CreateOrderCommand(
        @TargetAggregateIdentifier
        String orderId,
        String userId
) {
}

