package org.bookStore.order.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.GenericEventMessage;
import org.bookStore.common.events.CartClearFailedEvent;
import org.bookStore.common.events.CartClearedEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CartEventConsumer {

    private final ObjectMapper objectMapper;
    private final EventBus eventBus;

    @KafkaListener(topics = "cart-cleared", groupId = "order-saga")
    public void handleCartCleared(String payload) {
        try {
            CartClearedEvent event = objectMapper.readValue(payload, CartClearedEvent.class);
            log.info("[Kafka→Axon] CartClearedEvent received for orderId={}", event.orderId());

            eventBus.publish(GenericEventMessage.asEventMessage(event));
        } catch (Exception e) {
            log.error("Failed to process CartClearedEvent: {}", e.getMessage());
        }
    }

    @KafkaListener(topics = "cart-clear-failed", groupId = "order-saga")
    public void handleCartClearFailed(String payload) {
        try {
            CartClearFailedEvent event = objectMapper.readValue(payload, CartClearFailedEvent.class);
            log.info("[Kafka→Axon] CartClearFailedEvent received for orderId={}", event.orderId());

            eventBus.publish(GenericEventMessage.asEventMessage(event));
        } catch (Exception e) {
            log.error("Failed to process CartClearFailedEvent: {}", e.getMessage());
        }
    }
}

