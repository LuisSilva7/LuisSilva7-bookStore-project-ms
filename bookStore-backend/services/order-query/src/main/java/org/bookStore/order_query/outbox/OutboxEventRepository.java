package org.bookStore.order_query.outbox;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OutboxEventRepository extends JpaRepository<OutboxEvent, UUID> {
    List<OutboxEvent> findByPublishedFalse();

    boolean existsByAggregateIdAndEventType(String aggregateId, String eventType);
}

