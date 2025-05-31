package org.bookStore.composition.exception.custom;

public class AddCartItemException extends RuntimeException {

    public AddCartItemException(String message) {
        super(message);
    }
}
