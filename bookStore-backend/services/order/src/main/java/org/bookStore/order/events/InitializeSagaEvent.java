package org.bookStore.order.events;

import org.bookStore.common.utils.CreateOrderDetailsRequest;

import java.util.List;

public record InitializeSagaEvent(
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
