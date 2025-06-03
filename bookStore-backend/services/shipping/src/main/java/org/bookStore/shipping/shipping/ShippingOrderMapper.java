package org.bookStore.shipping.shipping;

import org.bookStore.common.commands.CreateShippingOrderCommand;
import org.springframework.stereotype.Service;

@Service
public class ShippingOrderMapper {

    public ShippingOrder toShippingOrder(CreateShippingOrderCommand command) {
        return ShippingOrder.builder()
                .firstName(command.firstName())
                .lastName(command.lastName())
                .address(command.address())
                .city(command.city())
                .email(command.email())
                .postalCode(command.postalCode())
                .orderId(command.orderId())
                .build();
    }

    public ShippingOrderResponse toShippingOrderResponse(ShippingOrder shippingOrder) {
        return new ShippingOrderResponse(
                shippingOrder.getFirstName(),
                shippingOrder.getAddress(),
                shippingOrder.getCity(),
                shippingOrder.getPostalCode()
        );
    }
}