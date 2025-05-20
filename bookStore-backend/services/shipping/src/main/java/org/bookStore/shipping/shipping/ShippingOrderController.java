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

    @PostMapping("/shipping")
    public ResponseEntity<ShippingOrder> createShippingOrder(@RequestBody ShippingOrder shippingOrder){
        ShippingOrder shippedOrder = shippingOrderService.createShippingOrder(shippingOrder);

        return new ResponseEntity<>(shippedOrder, HttpStatus.CREATED);
    }

    @GetMapping("/shipping")
    public ResponseEntity<List<ShippingOrder>> getAllShippingOrders(){
        List<ShippingOrder> shippedOrder = shippingOrderService.getAllShippingOrders();

        return new ResponseEntity<>(shippedOrder,HttpStatus.OK);
    }
}
