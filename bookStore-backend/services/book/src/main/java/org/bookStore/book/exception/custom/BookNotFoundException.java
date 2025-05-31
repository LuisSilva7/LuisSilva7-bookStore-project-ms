package org.bookStore.book.exception.custom;

public class BookNotFoundException extends RuntimeException{

    public BookNotFoundException(String msg) {
        super(msg);
    }
}