package org.bookStore.cart.cartItem;

import jakarta.validation.constraints.Min;

public record UpdateCartItemQuantityRequest(

        @Min(value = 0, message = "Quantity cannot be negative.")
        int quantity
) {}
