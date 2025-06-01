package org.bookStore.order.kafka.shipping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bookStore.order.command.commands.UpdateBookQuantityCommand;
import org.bookStore.order.command.events.ShippingOrderCreatedEvent;
import org.bookStore.order.kafka.book.KafkaBookProducer;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ShippingEventListener {

    private final KafkaBookProducer kafkaBookProducer;

    @KafkaListener(topics = "shipping-created", groupId = "order-group")
    public void consume(ShippingOrderCreatedEvent event) {
        log.info("📥 [Order] Recebido ShippingOrderCreatedEvent: {}", event);

        UpdateBookQuantityCommand command = new UpdateBookQuantityCommand(event.orderId(), event.userId());
        kafkaBookProducer.sendDecrementStockCommand(command);
    }
}
