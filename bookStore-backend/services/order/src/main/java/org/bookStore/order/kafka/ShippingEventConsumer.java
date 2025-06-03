package org.bookStore.order.kafka;

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

    private final EventBus eventBus;

    @KafkaListener(topics = "shipping-order-created", groupId = "saga")
    public void handle(ShippingOrderCreatedEvent event) {
        log.info("[Kafka→Axon] Forwarding ShippingOrderCreatedEvent to EventBus");

        eventBus.publish(GenericEventMessage.asEventMessage(event));
    }

    @KafkaListener(topics = "shipping-order-creation-failed", groupId = "saga")
    public void handle(ShippingOrderCreationFailedEvent event) {
        eventBus.publish(GenericEventMessage.asEventMessage(event));
    }
}
