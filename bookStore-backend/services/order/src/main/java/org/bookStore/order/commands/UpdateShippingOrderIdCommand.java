package org.bookStore.order.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;
import org.bookStore.common.utils.CreateOrderDetailsRequest;

import java.util.List;

public record UpdateShippingOrderIdCommand(
        @TargetAggregateIdentifier String orderId,
        Long userId,
        Long shippingOrderId,
        List<CreateOrderDetailsRequest> orderDetails
) {}
