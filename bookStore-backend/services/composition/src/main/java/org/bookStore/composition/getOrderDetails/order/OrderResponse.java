package org.bookStore.composition.getOrderDetails.order;

import org.bookStore.common.utils.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

public record OrderResponse(
        String id,
        LocalDateTime orderDate,
        double totalPrice,
        OrderStatus status,
        Long cartId,
        Long shippingOrderId,
        List<OrderDetailsResponse> orderDetails
) {
}
