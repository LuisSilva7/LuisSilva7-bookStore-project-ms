package org.bookStore.book.exception.custom;

public class AuthorAlreadyExistsException extends RuntimeException {

    public AuthorAlreadyExistsException(String message) {
        super(message);
    }
}
