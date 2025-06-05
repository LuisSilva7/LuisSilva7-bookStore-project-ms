package org.bookStore.order_query.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bookStore.common.events.BookInfoEvent;
import org.bookStore.order_query.order.OrderService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class BookEventListener {

    private final OrderService orderService;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "book-info", groupId = "order-query-group")
    public void handleBookInfoEvent(String payload) {
        try {
            BookInfoEvent event = objectMapper.readValue(payload, BookInfoEvent.class);
            log.info("BookInfoEvent received: {}", event);

            orderService.updateBooks(event);
        } catch (Exception e) {
            log.error("Failed to process BookInfoEvent: {}", e.getMessage());
        }
    }
}
