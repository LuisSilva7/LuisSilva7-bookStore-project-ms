package org.bookStore.order.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.GenericEventMessage;
import org.bookStore.common.events.ShippingOrderCreatedEvent;
import org.bookStore.common.events.ShippingOrderCreationFailedEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ShippingEventConsumer {

    private final ObjectMapper objectMapper;
    private final EventBus eventBus;

    @KafkaListener(topics = "shipping-order-created", groupId = "order-saga")
    public void handleShippingOrderCreated(String payload) {
        try {
            ShippingOrderCreatedEvent event = objectMapper.readValue(payload, ShippingOrderCreatedEvent.class);
            log.info("[Kafka→Axon] ShippingOrderCreatedEvent received for orderId={}", event.orderId());

            eventBus.publish(GenericEventMessage.asEventMessage(event));
        } catch (Exception e) {
            log.error("Failed to process ShippingOrderCreatedEvent: {}", e.getMessage());
        }
    }

    @KafkaListener(topics = "shipping-order-creation-failed", groupId = "order-saga")
    public void handleShippingOrderCreationFailed(String payload) {
        try {
            ShippingOrderCreationFailedEvent event = objectMapper.readValue(payload, ShippingOrderCreationFailedEvent.class);
            log.info("[Kafka→Axon] ShippingOrderCreationFailedEvent received for orderId={}", event.orderId());

            eventBus.publish(GenericEventMessage.asEventMessage(event));
        } catch (Exception e) {
            log.error("Failed to process ShippingOrderCreationFailedEvent: {}", e.getMessage());
        }
    }
}

