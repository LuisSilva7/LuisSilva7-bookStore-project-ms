package org.bookStore.cart.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bookStore.cart.cart.CartService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaConsumer {

    private final CartService cartService;

    @KafkaListener(topics = "cart-clear", groupId = "cart-group")
    public void handleClearCartCommand(ClearCartCommand command) {
        log.info("📥 [Cart] Recebido ClearCartCommand: {}", command);

        cartService.clearCart(command.getOrderId(), command.getUserId());

        log.info("🧺 [Cart] Carrinho limpo para userId={}", command.getUserId());
    }
}
