package org.bookStore.shipping.shipping;

import lombok.RequiredArgsConstructor;
import org.bookStore.shipping.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/shippings")
@RequiredArgsConstructor
public class ShippingOrderController {

    private final ShippingOrderService shippingOrderService;

    @PostMapping
    public ResponseEntity<ApiResponse<ShippingOrderResponse>> getShippingOrderByOrderId(
            @RequestBody GetShippingOrderRequest request) {
        ShippingOrderResponse shippingOrderResponse = shippingOrderService.
                getShippingOrderByOrderId(request.orderId());

        return ResponseEntity.ok(new ApiResponse<>(
                "ShippingOrder with id: " + request.orderId() + " obtained successfully!",
                shippingOrderResponse));
    }
}
