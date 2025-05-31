package org.bookStore.book.exception.custom;

public class SubCategoryAlreadyExistsException extends RuntimeException {

    public SubCategoryAlreadyExistsException(String message) {
        super(message);
    }
}
