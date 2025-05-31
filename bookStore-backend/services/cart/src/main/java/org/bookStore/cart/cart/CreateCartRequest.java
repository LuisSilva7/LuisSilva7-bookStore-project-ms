package org.bookStore.cart.cart;

import jakarta.validation.constraints.NotNull;

public record CreateCartRequest(
        @NotNull(message = "User ID is required.")
        Long userId
) {}