package org.bookStore.book.book;

import jakarta.validation.constraints.Min;

public record UpdateBookQuantityRequest(

        @Min(value = 0, message = "Quantity cannot be negative.")
        int quantity

) {}
