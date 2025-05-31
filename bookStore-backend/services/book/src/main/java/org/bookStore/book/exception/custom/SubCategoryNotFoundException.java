package org.bookStore.book.exception.custom;

public class SubCategoryNotFoundException extends RuntimeException{

    public SubCategoryNotFoundException(String msg) {
        super(msg);
    }
}
