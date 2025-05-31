package org.bookStore.composition.exception.custom;

public class BookUpdateException extends RuntimeException {

    public BookUpdateException(String message) {
        super(message);
    }
}
