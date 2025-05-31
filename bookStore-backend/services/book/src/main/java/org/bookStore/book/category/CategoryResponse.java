package org.bookStore.book.category;

import org.bookStore.book.subCategory.SubCategoryResponse;

import java.util.List;

public record CategoryResponse(
        Long id,
        String name,
        List<SubCategoryResponse> subCategories
) {
}
