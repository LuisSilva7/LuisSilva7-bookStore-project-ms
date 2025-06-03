package org.bookStore.composition.getOrderDetails.shippingOrder;

import org.bookStore.composition.authToken.FeignClientConfig;
import org.bookStore.composition.response.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(
        name = "shipping-order-service",
        url = "${application.config.shipping-order-url}",
        configuration = FeignClientConfig.class
)
public interface ShippingOrderClient {

    @PostMapping
    ApiResponse<ShippingOrderResponse> getShippingOrderByOrderId(@RequestBody GetShippingOrderRequest request);
}

