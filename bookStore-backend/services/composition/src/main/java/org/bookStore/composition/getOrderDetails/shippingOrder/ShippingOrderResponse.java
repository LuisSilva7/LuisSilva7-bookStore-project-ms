package org.bookStore.composition.getOrderDetails.shippingOrder;

public record ShippingOrderResponse(
        String firstname,
        String address,
        String city,
        String postalCode
) {
}
