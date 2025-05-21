package org.bookStore.composition;

import java.util.List;

public record CartDTO (
    Long userId,
    List<CartItemDTO> items,
    double totalPrice
) {
}
