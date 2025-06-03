package org.bookStore.order.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public record FinalizeOrderCommand(
        @TargetAggregateIdentifier
        String orderId
) {
}
