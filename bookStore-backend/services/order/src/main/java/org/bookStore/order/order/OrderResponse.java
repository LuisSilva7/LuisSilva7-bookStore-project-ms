package org.bookStore.order.order;

import org.bookStore.order.orderDetails.OrderDetailsResponse;

import java.time.LocalDateTime;
import java.util.List;

public record OrderResponse(
        Long id,
        LocalDateTime orderDate,
        double totalPrice,
        Long cartId,
        Long shippingOrderId,
        List<OrderDetailsResponse> orderDetails
) {}