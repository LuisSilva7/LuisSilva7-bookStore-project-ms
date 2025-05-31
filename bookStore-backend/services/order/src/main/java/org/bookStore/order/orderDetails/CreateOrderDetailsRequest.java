package org.bookStore.order.orderDetails;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreateOrderDetailsRequest(

        @NotNull(message = "Book ID is required.")
        Long bookId,

        @Min(value = 1, message = "Quantity must be at least 1.")
        int quantity,

        @Positive(message = "Unit price must be positive.")
        double unitPrice,

        @Positive(message = "Subtotal must be positive.")
        double subTotal

) {}
