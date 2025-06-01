package org.bookStore.order.kafka.book;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bookStore.order.command.commands.ClearCartCommand;
import org.bookStore.order.command.events.BookQuantityUpdatedEvent;
import org.bookStore.order.kafka.cart.KafkaCartProducer;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class BookEventListener {

    private final KafkaCartProducer cartProducer;

    @KafkaListener(topics = "book-updated", groupId = "order-group")
    public void onBookStockUpdated(BookQuantityUpdatedEvent event) {
        log.info("📥 [Order] Recebido BookStockUpdatedEvent: {}", event);

        ClearCartCommand command = new ClearCartCommand(event.orderId(), event.userId());
        cartProducer.sendClearCartCommand(command);

        log.info("📤 [Order] Enviado comando para limpar carrinho.");
    }
}

