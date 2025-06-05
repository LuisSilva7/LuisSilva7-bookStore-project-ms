package org.bookStore.order_query.outbox;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OutboxEventRepository extends JpaRepository<OutboxEvent, UUID> {
    boolean existsByAggregateIdAndEventType(String aggregateId, String eventType);
}

