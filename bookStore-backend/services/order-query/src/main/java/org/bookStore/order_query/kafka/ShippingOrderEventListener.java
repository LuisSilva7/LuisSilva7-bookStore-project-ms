package org.bookStore.order_query.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
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
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "shipping-info", groupId = "order-query-group")
    public void handleShippingInfoEvent(String payload) {
        try {
            ShippingInfoEvent event = objectMapper.readValue(payload, ShippingInfoEvent.class);
            log.info("ShippingInfoEvent received: {}", event);

            orderService.updateShippingInfo(event);
        } catch (Exception e) {
            log.error("Failed to process ShippingInfoEvent: {}", e.getMessage());
        }
    }
}
