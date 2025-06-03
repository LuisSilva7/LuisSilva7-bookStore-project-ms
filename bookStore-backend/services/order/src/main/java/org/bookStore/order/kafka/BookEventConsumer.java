package org.bookStore.order.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.GenericEventMessage;
import org.bookStore.common.events.BookQuantityRollbackedEvent;
import org.bookStore.common.events.BookQuantityUpdateFailedEvent;
import org.bookStore.common.events.BookQuantityUpdatedEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class BookEventConsumer {

    private final EventBus eventBus;

    @KafkaListener(topics = "book-quantity-updated", groupId = "saga")
    public void handle(BookQuantityUpdatedEvent event) {
        log.info("[Kafka→Axon] Forwarding BookQuantityUpdatedEvent to EventBus");

        eventBus.publish(GenericEventMessage.asEventMessage(event));
    }

    @KafkaListener(topics = "book-quantity-rollbacked", groupId = "saga")
    public void handle(BookQuantityRollbackedEvent event) {
        log.info("[Kafka→Axon] Forwarding BookQuantityRollbackedEvent to EventBus");
        eventBus.publish(GenericEventMessage.asEventMessage(event));
    }

    @KafkaListener(topics = "book-quantity-update-failed")
    public void handle(BookQuantityUpdateFailedEvent event) {
        eventBus.publish(GenericEventMessage.asEventMessage(event));
    }
}

