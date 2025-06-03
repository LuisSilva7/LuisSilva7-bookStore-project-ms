package org.bookStore.order.command.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;
import org.bookStore.common.utils.CreateOrderDetailsRequest;

import java.util.List;

public record CreateOrderCommand(
        @TargetAggregateIdentifier
        String orderId,
        Long userId,
        List<CreateOrderDetailsRequest> orderDetails,

        String firstName,
        String lastName,
        String address,
        String city,
        String email,
        String postalCode
) {
}

