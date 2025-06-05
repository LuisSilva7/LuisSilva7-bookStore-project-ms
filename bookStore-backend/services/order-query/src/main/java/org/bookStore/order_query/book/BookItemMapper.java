package org.bookStore.order_query.book;

import org.springframework.stereotype.Service;

@Service
public class BookItemMapper {

    public BookItemResponse toBookItemResponse(BookItem item) {
        return new BookItemResponse(
                item.getBookId(),
                item.getUnitPrice(),
                item.getQuantity()
        );
    }
}

