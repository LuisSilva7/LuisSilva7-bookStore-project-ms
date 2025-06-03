package org.bookStore.order_query.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bookStore.common.events.OrderInfoFinalEvent;
import org.bookStore.order_query.order.OrderService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderFinalEventListener {

    private final OrderService orderService;

    @KafkaListener(topics = "order-events-4", groupId = "order-query-group")
    public void handle(OrderInfoFinalEvent event) {
        log.info("Received OrderQueryUpdateEvent: {}", event);
        orderService.updateOrderStatus(event);
    }
}
