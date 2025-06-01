package org.bookStore.order.order;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.bookStore.order.response.ApiResponse;
import org.bookStore.order.response.PageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<ApiResponse<OrderResponse>> createOrder(@RequestBody @Valid CreateOrderRequest request) {
        Order savedOrder = orderService.createOrder(order);
        return new ResponseEntity<>(savedOrder, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<Order>>> getAllOrders(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size
    ) {
        PageResponse<Order> orders = orderService.getAllOrders(page, size);

        return ResponseEntity.ok(new ApiResponse<>(
                "Orders obtained successfully!", orders));
    }

}
