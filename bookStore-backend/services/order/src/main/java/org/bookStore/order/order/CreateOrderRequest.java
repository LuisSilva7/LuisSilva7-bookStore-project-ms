package org.bookStore.order.order;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import org.bookStore.common.utils.CreateOrderDetailsRequest;

import java.util.List;

public record CreateOrderRequest(

        @NotEmpty(message = "Order should have 1 item at least")
        List<CreateOrderDetailsRequest> orderDetails,

        @NotBlank String firstName,
        @NotBlank String lastName,
        @NotBlank String address,
        @NotBlank String city,
        @Email String email,
        @NotBlank String postalCode
) {}
