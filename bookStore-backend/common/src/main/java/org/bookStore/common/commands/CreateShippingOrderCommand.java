package org.bookStore.common.commands;

import org.bookStore.common.utils.CreateOrderDetailsRequest;

import java.util.List;

public record CreateShippingOrderCommand(
        String orderId,
        Long userId,
        String firstName,
        String lastName,
        String address,
        String city,
        String email,
        String postalCode,
        List<CreateOrderDetailsRequest> orderDetails
) {
}