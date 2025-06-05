package org.bookStore.cart.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bookStore.cart.cart.CartService;
import org.bookStore.cart.outbox.OutboxEventService;
import org.bookStore.common.commands.ClearCartCommand;
import org.bookStore.common.commands.UpdateBookQuantityCommand;
import org.bookStore.common.events.CartClearFailedEvent;
import org.bookStore.common.events.CartClearedEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartCommandListener {

    private final CartService cartService;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private final OutboxEventService outboxEventService;

    @KafkaListener(topics = "clear-cart", groupId = "cart")
    public void handleClearCart(String payload) throws JsonProcessingException {
        ClearCartCommand command = objectMapper.readValue(payload, ClearCartCommand.class);

        log.info("Received ClearCartCommand for orderId={}", command.orderId());

        try {
            cartService.clearCartByUserId(command);

        } catch (Exception e) {
            log.error("Error clearing cart for userId={}, orderId={}", command.userId(), command.orderId(), e);

            CartClearFailedEvent failedEvent = new CartClearFailedEvent(
                    command.orderId(),
                    command.userId(),
                    command.orderDetails()
            );

            outboxEventService.saveEvent(
                    command.orderId(),
                    CartClearFailedEvent.class.getSimpleName(),
                    failedEvent
            );

            log.info("CartClearFailedEvent saved to outbox for orderId={}", command.orderId());
        }
    }
}

