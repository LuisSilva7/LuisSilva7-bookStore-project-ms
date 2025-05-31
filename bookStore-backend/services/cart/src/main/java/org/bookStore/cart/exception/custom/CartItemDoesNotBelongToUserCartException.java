package org.bookStore.cart.exception.custom;

public class CartItemDoesNotBelongToUserCartException extends RuntimeException {

    public CartItemDoesNotBelongToUserCartException(String message) {
        super(message);
    }
}
