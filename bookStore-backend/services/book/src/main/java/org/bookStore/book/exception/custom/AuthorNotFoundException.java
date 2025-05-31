package org.bookStore.book.exception.custom;

public class AuthorNotFoundException extends RuntimeException {

    public AuthorNotFoundException(String message) {
        super(message);
    }
}
