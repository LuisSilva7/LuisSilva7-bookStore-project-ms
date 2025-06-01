package org.bookStore.order.kafka.cart;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bookStore.order.command.commands.FinalizeOrderCommand;
import org.bookStore.order.command.events.CartClearedEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.axonframework.commandhandling.gateway.CommandGateway;

@Slf4j
@Component
@RequiredArgsConstructor
public class CartEventListener {

    private final CommandGateway commandGateway;

    @KafkaListener(topics = "cart-cleared", groupId = "order-group")
    public void onCartCleared(CartClearedEvent event) {
        log.info("📥 [Order] Recebido CartClearedEvent: {}", event);

        // Enviar comando para mudar estado da Order
        FinalizeOrderCommand finalizeCommand = new FinalizeOrderCommand(event.orderId(), event.userId());
        commandGateway.send(finalizeCommand);

        log.info("✅ [Order] Enviado comando para finalizar Order.");
    }
}