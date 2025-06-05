package org.bookStore.order_query.outbox;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class OutboxEventService {

    @Autowired
    private OutboxEventRepository outboxRepository;

    @Autowired
    private ObjectMapper objectMapper;

    public <T> void saveEvent(String aggregateId, String eventType, T payload) {
        try {
            boolean exists = outboxRepository.existsByAggregateIdAndEventType(aggregateId, eventType);
            if (exists) {
                return;
            }

            String jsonPayload = objectMapper.writeValueAsString(payload);

            OutboxEvent event = new OutboxEvent();
            event.setId(UUID.randomUUID());
            event.setAggregateId(aggregateId);
            event.setEventType(eventType);
            event.setPayload(jsonPayload);
            event.setCreatedAt(LocalDateTime.now());
            event.setStatus("PROCESSING");

            outboxRepository.save(event);
        } catch (Exception e) {
            throw new RuntimeException("Failed to save outbox event", e);
        }
    }
}