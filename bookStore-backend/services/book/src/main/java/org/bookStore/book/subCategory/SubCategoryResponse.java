package org.bookStore.book.subCategory;

import org.bookStore.book.category.CategoryResponse;

public record SubCategoryResponse(
        Long id,
        String name,
        CategoryResponse category
) {}
