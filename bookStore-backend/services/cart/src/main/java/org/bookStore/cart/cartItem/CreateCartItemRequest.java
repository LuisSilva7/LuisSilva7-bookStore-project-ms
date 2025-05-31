package org.bookStore.cart.cartItem;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record CreateCartItemRequest(

        @NotNull(message = "Quantity is required.")
        @Min(value = 1, message = "Quantity must be at least 1.")
        Integer quantity,

        @NotNull(message = "Unit price is required.")
        @Min(value = 0, message = "Unit price must be zero or positive.")
        Double unitPrice,

        @NotNull(message = "Book ID is required.")
        Long bookId
) {}