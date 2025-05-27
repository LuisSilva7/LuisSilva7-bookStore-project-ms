package org.bookStore.order.order.kafka;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderCreatedEvent {
    private Long id;
    private Long userId;
    private Double totalAmount;
    private LocalDate orderDate;
    private String status;
}
