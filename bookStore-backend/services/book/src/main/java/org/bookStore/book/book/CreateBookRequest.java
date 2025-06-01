package org.bookStore.book.book;

import jakarta.validation.constraints.*;

import java.util.Set;

public record CreateBookRequest(

        @NotBlank(message = "Title is required.")
        String title,

        @NotBlank(message = "ISBN number is required.")
        String isbnNumber,

        @Size(max = 1000, message = "Description must not exceed 1000 characters.")
        String description,

        @Positive(message = "Price must be a positive value.")
        double price,

        @Min(value = 0, message = "Quantity cannot be negative.")
        int quantity,

        @NotNull(message = "Category ID is required.")
        Long categoryId,

        @NotNull(message = "Author ID is required.")
        Long authorId,

        @NotEmpty(message = "At least one subcategory ID must be provided.")
        Set<Long> subcategoryIds

) {}