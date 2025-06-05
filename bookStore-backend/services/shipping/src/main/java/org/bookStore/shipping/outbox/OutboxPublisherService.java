package org.bookStore.shipping.outbox;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class OutboxPublisherService {

    private final OutboxEventRepository outboxRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    private static final Map<String, String> EVENT_TOPICS = Map.of(
            "ShippingOrderCreatedEvent", "shipping-order-created",
            "ShippingOrderCreationFailedEvent", "shipping-order-creation-failed",
            "ShippingInfoEvent", "shipping-info"
    );

    @Scheduled(fixedRate = 5000)
    public void publishOutboxEvents() {
        List<OutboxEvent> events = outboxRepository.findByPublishedFalse();

        for (OutboxEvent event : events) {
            try {
                String topic = EVENT_TOPICS.get(event.getEventType());

                if (topic == null) {
                    log.error("[OutboxPublisher] Event type not found: {}", event.getEventType());
                    continue;
                }

                kafkaTemplate.send(topic, event.getAggregateId(), event.getPayload());

                event.setPublished(true);
                event.setStatus("PUBLISHED");
                outboxRepository.save(event);
            } catch (Exception e) {
                event.setStatus("FAILED");
                outboxRepository.save(event);
                log.error("[OutboxPublisher] Failed to publish event: {}", e.getMessage());
            }
        }
    }
}