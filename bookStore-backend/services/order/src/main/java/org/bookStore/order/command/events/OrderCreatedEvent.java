package org.bookStore.order.command.events;

import org.bookStore.common.dto.CreateOrderDetailsRequest;

import java.util.List;

public record OrderCreatedEvent(
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