package org.bookStore.order.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
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
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "book-quantity-updated", groupId = "order-saga")
    public void handleBookQuantityUpdated(String payload) {
        try {
            BookQuantityUpdatedEvent event = objectMapper.readValue(payload, BookQuantityUpdatedEvent.class);
            log.info("[Kafka→Axon] BookQuantityUpdatedEvent received for orderId={}", event.orderId());

            eventBus.publish(GenericEventMessage.asEventMessage(event));
        } catch (Exception e) {
            log.error("Failed to process BookQuantityUpdatedEvent: {}", e.getMessage());
        }
    }

    @KafkaListener(topics = "book-quantity-update-failed", groupId = "order-saga")
    public void handleBookQuantityUpdateFailed(String payload) {
        try {
            BookQuantityUpdateFailedEvent event = objectMapper.readValue(payload, BookQuantityUpdateFailedEvent.class);
            log.info("[Kafka→Axon] BookQuantityUpdateFailedEvent received for orderId={}", event.orderId());

            eventBus.publish(GenericEventMessage.asEventMessage(event));
        } catch (Exception e) {
            log.error("Failed to process BookQuantityUpdateFailedEvent: {}", e.getMessage());
        }
    }

    @KafkaListener(topics = "book-quantity-rollbacked", groupId = "order-saga")
    public void handleBookQuantityRollbacked(String payload) {
        try {
            BookQuantityRollbackedEvent event = objectMapper.readValue(payload, BookQuantityRollbackedEvent.class);
            log.info("[Kafka→Axon] BookQuantityRollbackedEvent received for orderId={}", event.orderId());

            eventBus.publish(GenericEventMessage.asEventMessage(event));
        } catch (Exception e) {
            log.error("Failed to process BookQuantityRollbackedEvent: {}", e.getMessage());
        }
    }
}

