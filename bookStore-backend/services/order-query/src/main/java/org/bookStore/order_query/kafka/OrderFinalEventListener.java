package org.bookStore.order_query.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
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
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "order-info-final", groupId = "order-query-group")
    public void handleOrderInfoFinalEvent(String payload) {
        try {
            OrderInfoFinalEvent event = objectMapper.readValue(payload, OrderInfoFinalEvent.class);
            log.info("OrderInfoFinalEvent received: {}", event);

            orderService.updateFinalOrderStatus(event);
        } catch (Exception e) {
            log.error("Failed to process OrderInfoFinalEvent: {}", e.getMessage());
        }
    }
}
