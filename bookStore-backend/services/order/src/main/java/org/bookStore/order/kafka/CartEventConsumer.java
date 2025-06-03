package org.bookStore.order.kafka;

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

    private final EventBus eventBus;

    @KafkaListener(topics = "cart-cleared", groupId = "saga")
    public void handle(CartClearedEvent event) {
        log.info("[Kafka→Axon] Forwarding CartClearedEvent to EventBus");

        eventBus.publish(GenericEventMessage.asEventMessage(event));
    }

    @KafkaListener(topics = "cart-clear-failed", groupId = "saga")
    public void handle(CartClearFailedEvent event) {
        log.info("[Kafka→Axon] Forwarding CartClearFailedEvent to EventBus");
        eventBus.publish(GenericEventMessage.asEventMessage(event));
    }
}

