package org.bookStore.order.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.GenericEventMessage;
import org.bookStore.order.events.InitializeSagaEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderEventConsumer {

    private final ObjectMapper objectMapper;
    private final EventBus eventBus;

    @KafkaListener(topics = "order-created", groupId = "order-saga")
    public void consume(String payload) {
        try {
            InitializeSagaEvent event = objectMapper.readValue(payload, InitializeSagaEvent.class);

            System.out.println("[KafkaListener] InitializeSagaEvent received with orderId=" + event.orderId());

            eventBus.publish(GenericEventMessage.asEventMessage(event));

        } catch (Exception e) {
            log.error("Error handling OrderCreatedEvent: {}", e.getMessage());
        }
    }
}
