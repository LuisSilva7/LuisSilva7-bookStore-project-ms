package org.bookStore.order_query.kafka;

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

    @KafkaListener(topics = "order-events-3", groupId = "order-query-group")
    public void handle(BookInfoEvent event) {
        log.info("Received BookInfoEvent: {}", event);
        orderService.updateBooks(event);
    }
}
