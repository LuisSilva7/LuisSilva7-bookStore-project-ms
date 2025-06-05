package org.bookStore.composition.getOrderDetails;

import org.bookStore.common.utils.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

public record OrderDetailsResponse(
        String orderId,
        LocalDateTime orderDate,
        Double totalPrice,
        OrderStatus status,
        Long userId,
        String firstname,
        String address,
        String city,
        String postalCode,
        List<OrderDetailsItemResponse> items
) {
}
