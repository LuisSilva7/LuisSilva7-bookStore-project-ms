package org.bookStore.composition.getOrderDetails.order;

import java.time.LocalDateTime;
import java.util.List;

public record OrderResponse(
        String id,
        LocalDateTime orderDate,
        double totalPrice,
        Long cartId,
        Long shippingOrderId,
        List<OrderDetailsResponse> orderDetails
) {
}
