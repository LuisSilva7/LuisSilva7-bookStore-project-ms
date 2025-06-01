package org.bookStore.book.book;

import org.bookStore.book.author.AuthorResponse;
import org.bookStore.book.category.CategoryResponse;
import org.bookStore.book.subCategory.SubCategoryResponse;

import java.util.Set;

public record BookResponse(
        Long id,
        String title,
        String isbnNumber,
        String description,
        double price,
        int quantity,
        CategoryResponse category,
        AuthorResponse author,
        Set<SubCategoryResponse> subcategories
) {
}
