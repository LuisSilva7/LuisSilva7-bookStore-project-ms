package org.bookStore.book.exception.custom;

public class CategoryNotFoundException extends RuntimeException{

    public CategoryNotFoundException(String msg) {
        super(msg);
    }
}