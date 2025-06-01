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

@Slf4j
@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final CommandGateway commandGateway;

    @PostMapping
    public ResponseEntity<String> checkout(@RequestParam String userId) {
        String orderId = UUID.randomUUID().toString();

        log.info("🛒 [Checkout] Iniciando checkout para userId={} com orderId={}", userId, orderId);

        CreateOrderCommand command = new CreateOrderCommand(orderId, userId);
        commandGateway.send(command);

        return ResponseEntity.ok("Checkout iniciado com sucesso para orderId=" + orderId);
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
