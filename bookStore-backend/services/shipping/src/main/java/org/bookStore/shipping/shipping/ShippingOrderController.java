package org.bookStore.shipping.shipping;

import lombok.RequiredArgsConstructor;
import org.bookStore.shipping.response.ApiResponse;
import org.bookStore.shipping.response.PageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/shippings")
@RequiredArgsConstructor
public class ShippingOrderController {

    private final ShippingOrderService shippingOrderService;

    @PostMapping
    public ResponseEntity<ShippingOrder> createShippingOrder(@RequestBody ShippingOrder shippingOrder) {
        ShippingOrder createdShippingOrder = shippingOrderService.createShippingOrder(shippingOrder);
        return new ResponseEntity<>(createdShippingOrder, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<ShippingOrder>>> getAllShippingOrders(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size
    ) {
        PageResponse<ShippingOrder> shippingOrders = shippingOrderService.getAllShippingOrders(page, size);

        return ResponseEntity.ok(new ApiResponse<>(
                "Shipping orders obtained successfully!", shippingOrders));
    }
}
