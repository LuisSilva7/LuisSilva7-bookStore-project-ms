package org.bookStore.shipping.exception.custom;

public class ShippingOrderNotFoundException extends RuntimeException {

    public ShippingOrderNotFoundException(String message) {
        super(message);
    }
}
