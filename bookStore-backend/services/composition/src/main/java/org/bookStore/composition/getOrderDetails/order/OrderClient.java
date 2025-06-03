package org.bookStore.composition.getOrderDetails.order;

import org.bookStore.composition.authToken.FeignClientConfig;
import org.bookStore.composition.response.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "order-service",
        url = "${application.config.order-url}",
        configuration = FeignClientConfig.class
)
public interface OrderClient {

    @GetMapping("/{id}")
    ApiResponse<OrderResponse> getOrderById(@PathVariable("id") String orderId);
}

