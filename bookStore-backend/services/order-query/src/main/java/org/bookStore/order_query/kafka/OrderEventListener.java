package org.bookStore.order_query.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
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
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "order-info", groupId = "order-query-group")
    public void handleOrderInfoEvent(String payload) {
        try {
            OrderInfoEvent event = objectMapper.readValue(payload, OrderInfoEvent.class);
            log.info("OrderInfoEvent received: {}", event);

            orderService.createOrder(event);
        } catch (Exception e) {
            log.error("Failed to process OrderInfoEvent: {}", e.getMessage());
        }
    }
}
