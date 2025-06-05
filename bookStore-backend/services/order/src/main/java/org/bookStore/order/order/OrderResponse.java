package org.bookStore.order.order;

import org.bookStore.order.orderDetails.OrderDetailsResponse;

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
) {}