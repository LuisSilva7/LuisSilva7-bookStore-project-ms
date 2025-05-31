package org.bookStore.book.subCategory;

import jakarta.validation.constraints.NotBlank;

public record CreateSubCategoryRequest(
        @NotBlank(message = "SubCategory name is required.")
        String name
) {}