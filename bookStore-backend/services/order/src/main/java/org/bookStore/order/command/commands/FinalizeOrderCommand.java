package org.bookStore.order.command.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public record FinalizeOrderCommand(
        @TargetAggregateIdentifier
        String orderId
) {
}
