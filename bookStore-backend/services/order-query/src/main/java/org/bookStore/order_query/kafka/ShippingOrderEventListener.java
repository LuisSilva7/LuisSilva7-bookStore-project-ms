package org.bookStore.order_query.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bookStore.common.events.ShippingInfoEvent;
import org.bookStore.order_query.order.OrderService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ShippingOrderEventListener {

    private final OrderService orderService;

    @KafkaListener(topics = "order-events-2", groupId = "order-query-group")
    public void handle(ShippingInfoEvent event) {
        log.info("Received ShippingInfoUpdatedEvent: {}", event);
        orderService.updateShippingInfo(event);
    }
}
