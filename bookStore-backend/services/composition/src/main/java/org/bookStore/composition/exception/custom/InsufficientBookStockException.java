package org.bookStore.composition.exception.custom;

public class InsufficientBookStockException extends RuntimeException {

    public InsufficientBookStockException(String message) {
        super(message);
    }
}
