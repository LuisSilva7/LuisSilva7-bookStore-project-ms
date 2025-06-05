package org.bookStore.order_query.outbox;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "outbox_event_order_query",
        uniqueConstraints = @UniqueConstraint(columnNames = {"aggregate_id", "event_type"}))
public class OutboxEvent {

    @Id
    private UUID id;

    private String aggregateId;

    private String eventType;

    @Column(columnDefinition = "TEXT")
    private String payload;

    private LocalDateTime createdAt = LocalDateTime.now();

    private boolean published = false;

    private String status;
}
