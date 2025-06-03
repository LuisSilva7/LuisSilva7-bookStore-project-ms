package org.bookStore.order.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public record UpdateShippingOrderIdCommand(
        @TargetAggregateIdentifier String orderId,
        Long shippingOrderId
) {}
