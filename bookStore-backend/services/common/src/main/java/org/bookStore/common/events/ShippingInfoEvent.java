package org.bookStore.common.events;

public record ShippingInfoEvent(
        String orderId,
        String firstName,
        String address,
        String city,
        String postalCode
) {
}
