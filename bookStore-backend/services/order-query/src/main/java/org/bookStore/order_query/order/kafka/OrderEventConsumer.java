package org.bookStore.order_query.order.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bookStore.order_query.order.Order;
import org.bookStore.order_query.order.OrderRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderEventConsumer {

    private final OrderRepository orderRepository;

    @KafkaListener(
            topics = "order-created",
            groupId = "order-query-group",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void consume(OrderCreatedEvent event) {
        log.info("Received OrderCreatedEvent: {}", event);

        Order order = Order.builder()
                .id(event.getId())
                .userId(event.getUserId())
                .totalAmount(event.getTotalAmount())
                .orderDate(event.getOrderDate())
                .status(event.getStatus())
                .build();

        orderRepository.save(order);
    }
}