package org.bookStore.order.orderDetails;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/order-details")
@RequiredArgsConstructor
public class OrderDetailsController {

    private final OrderDetailsService orderDetailsService;

    @GetMapping("/order/{id}")
    public ResponseEntity<List<OrderDetails>> getOrderDetailsByOrderId(@PathVariable("id") Long orderId) {
        List<OrderDetails> details = orderDetailsService.getOrderDetailsByOrderId(orderId);
        return ResponseEntity.ok(details);
    }
}
