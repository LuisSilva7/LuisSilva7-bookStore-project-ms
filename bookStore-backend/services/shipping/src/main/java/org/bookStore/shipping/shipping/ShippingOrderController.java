package org.bookStore.shipping.shipping;

import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<List<ShippingOrder>> getAllShippingOrders() {
        List<ShippingOrder> shippingOrders = shippingOrderService.getAllShippingOrders();
        return ResponseEntity.ok(shippingOrders);
    }
}
