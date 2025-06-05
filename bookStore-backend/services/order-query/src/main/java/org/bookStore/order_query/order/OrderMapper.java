package org.bookStore.order_query.order;

import lombok.RequiredArgsConstructor;
import org.bookStore.order_query.book.BookItemMapper;
import org.bookStore.order_query.book.BookItemResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderMapper {

    private final BookItemMapper bookItemMapper;

    public OrderResponse toOrderResponse(Order order) {
        List<BookItemResponse> bookItemDTOs = order.getBookItems()
                .stream()
                .map(bookItemMapper::toBookItemResponse)
                .toList();

        return new OrderResponse(
                order.getId(),
                order.getOrderId(),
                order.getOrderDate(),
                order.getTotalPrice(),
                order.getStatus().name(),
                order.getUserId(),
                order.getFirstname(),
                order.getAddress(),
                order.getCity(),
                order.getPostalCode(),
                bookItemDTOs
        );
    }
}