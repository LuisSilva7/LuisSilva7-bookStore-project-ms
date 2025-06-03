package org.bookStore.shipping.shipping;

public record ShippingOrderResponse(
        String firstname,
        String address,
        String city,
        String postalCode
) {
}
