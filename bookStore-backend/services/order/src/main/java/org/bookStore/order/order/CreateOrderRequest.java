package org.bookStore.order.order;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.bookStore.order.orderDetails.CreateOrderDetailsRequest;

import java.time.LocalDateTime;
import java.util.List;

public record CreateOrderRequest(

        @NotNull(message = "Order date is required.")
        LocalDateTime orderDate,

        @Positive(message = "Total price must be positive.")
        double totalPrice,

        @NotNull(message = "Cart ID is required.")
        Long cartId,

        @NotNull(message = "Shipping order ID is required.")
        Long shippingOrderId,

        @NotNull(message = "Order details are required.")
        List<@NotNull CreateOrderDetailsRequest> orderDetails

) {}
