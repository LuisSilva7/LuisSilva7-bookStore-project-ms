package org.bookStore.order_query.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bookStore.common.events.OrderInfoCancelledEvent;
import org.bookStore.order_query.order.OrderService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderCancelledEventListener {

    private final OrderService orderService;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "order-info-cancelled", groupId = "order-query-group")
    public void handleOrderInfoFinalEvent(String payload) {
        try {
            OrderInfoCancelledEvent event = objectMapper.readValue(payload, OrderInfoCancelledEvent.class);
            log.info("OrderInfoCancelledEvent received: {}", event);

            orderService.updateCancelledOrderStatus(event);
        } catch (Exception e) {
            log.error("Failed to process OrderInfoCancelledEvent: {}", e.getMessage());
        }
    }
}