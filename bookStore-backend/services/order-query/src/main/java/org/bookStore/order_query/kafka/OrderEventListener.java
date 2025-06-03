package org.bookStore.order_query.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bookStore.common.events.OrderInfoEvent;
import org.bookStore.order_query.order.OrderService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderEventListener {

    private final OrderService orderService;

    @KafkaListener(topics = "order-events-1", groupId = "order-query-group")
    public void handle(OrderInfoEvent event) {
        log.info("Received OrderQueryUpdateEvent: {}", event);
        orderService.createOrder(event);
    }
}
