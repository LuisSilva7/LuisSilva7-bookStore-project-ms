package org.bookStore.cart.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bookStore.cart.cart.CartService;
import org.bookStore.common.commands.ClearCartCommand;
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

    @KafkaListener(topics = "clear-cart", groupId = "cart")
    @Transactional
    public void handleClearCart(ClearCartCommand command) {
        try {
            cartService.clearCartByUserId(command.userId());

            kafkaTemplate.send("cart-cleared", command.orderId(), new CartClearedEvent(
                    command.orderId(), command.userId()));
            log.info("CartClearedEvent sent");

        } catch (Exception e) {
            log.error("Error clearing cart for userId={}, orderId={}", command.userId(), command.orderId(), e);

            kafkaTemplate.send("cart-clear-failed", command.orderId(), new CartClearFailedEvent(
                    command.orderId(), command.userId(), command.orderDetails()));
        }
    }
}

