package org.bookStore.order_query.order;

import org.bookStore.order_query.book.BookItemResponse;

import java.time.LocalDateTime;
import java.util.List;

public record OrderResponse(
        Long id,
        String orderId,
        LocalDateTime orderDate,
        Double totalPrice,
        String status,
        Long userId,
        String firstname,
        String address,
        String city,
        String postalCode,
        List<BookItemResponse> bookItems
) {
}
