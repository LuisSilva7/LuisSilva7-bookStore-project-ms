package org.bookStore.book.book;

import org.bookStore.book.author.AuthorResponse;
import org.bookStore.book.category.CategoryResponse;

public record BookResponse(
        Long id,
        String title,
        String isbnNumber,
        String description,
        double price,
        int quantity,
        CategoryResponse category,
        AuthorResponse author
) {
}
