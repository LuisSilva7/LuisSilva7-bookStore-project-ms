package org.bookStore.order.order;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.bookStore.order.command.commands.CreateOrderCommand;
import org.bookStore.order.response.ApiResponse;
import org.bookStore.order.response.PageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import static org.springframework.http.HttpStatus.CREATED;

@Slf4j
@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final CommandGateway commandGateway;

    @PostMapping
    public CompletableFuture<ResponseEntity<ApiResponse<String>>> checkout(
            @RequestHeader("x-userid") Long userId,
            @RequestBody @Valid CreateOrderRequest request) {
        String orderId = UUID.randomUUID().toString();

        log.info("[Checkout] Starting checkout for userId={} with orderId={}", userId, orderId);

        CreateOrderCommand command = new CreateOrderCommand(
                orderId,
                userId,
                request.orderDetails(),
                request.firstName(),
                request.lastName(),
                request.address(),
                request.city(),
                request.email(),
                request.postalCode()
        );

        return commandGateway.send(command)
                .thenApply(result -> ResponseEntity
                        .status(HttpStatus.ACCEPTED)
                        .body(new ApiResponse<>("Order received and is being processed.", orderId)));
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
