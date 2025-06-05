package org.bookStore.order_query.order;

import org.bookStore.order_query.book.BookItemResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderMapper {

    public OrderResponse toOrderResponse(Order order) {
        List<BookItemResponse> bookItemDTOs = order.getBookItems()
                .stream()
                .map(bookItem -> new BookItemResponse(
                        bookItem.getBookId(),
                        bookItem.getUnitPrice(),
                        bookItem.getQuantity()
                ))
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