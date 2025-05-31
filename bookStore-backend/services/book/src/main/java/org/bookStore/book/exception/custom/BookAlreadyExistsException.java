package org.bookStore.book.exception.custom;

public class BookAlreadyExistsException extends RuntimeException {

    public BookAlreadyExistsException(String message) {
        super(message);
    }
}
