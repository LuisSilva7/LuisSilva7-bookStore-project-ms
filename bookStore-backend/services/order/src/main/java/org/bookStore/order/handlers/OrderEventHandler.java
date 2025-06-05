package org.bookStore.order.handlers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.bookStore.order.events.OrderCancelledEvent;
import org.bookStore.order.events.OrderCreatedEvent;
import org.bookStore.order.events.OrderFinalizedEvent;
import org.bookStore.order.events.ShippingOrderIdUpdatedEvent;
import org.bookStore.order.exception.custom.OrderNotFoundException;
import org.bookStore.order.order.Order;
import org.bookStore.order.order.OrderMapper;
import org.bookStore.order.order.OrderRepository;
import org.bookStore.order.outbox.OutboxEventService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static org.bookStore.order.order.OrderStatus.CANCELLED;
import static org.bookStore.order.order.OrderStatus.FINALIZED;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderEventHandler {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final OutboxEventService outboxEventService;

    @EventHandler
    @Transactional
    public void on(OrderCreatedEvent event) {
        Order order = orderMapper.toOrder(event);
        orderRepository.save(order);

        outboxEventService.saveEvent(
                event.orderId(),
                event.getClass().getSimpleName(),
                event
        );

        log.info("OrderCreatedEvent saved to outbox for orderId={}", event.orderId());
    }

    @EventHandler
    @Transactional
    public void on(ShippingOrderIdUpdatedEvent event) {
        Order order = orderRepository.findById(event.orderId())
                .orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + event.orderId()));

        log.info("Order being updated with shippingOrderId={} in the database", order);

        order.setShippingOrderId(event.shippingOrderId());
        orderRepository.save(order);

        outboxEventService.saveEvent(
                event.orderId(),
                event.getClass().getSimpleName(),
                event
        );

        log.info("Order updated with shippingOrderId={} in the database", event.shippingOrderId());
    }

    @EventHandler
    public void on(OrderFinalizedEvent event) {
        Order order = orderRepository.findById(event.orderId())
                .orElseThrow(() -> new OrderNotFoundException("Order not found"));

        order.setStatus(FINALIZED);
        orderRepository.save(order);
    }

    @EventHandler
    public void on(OrderCancelledEvent event) {
        Order order = orderRepository.findById(event.orderId())
                .orElseThrow(() -> new OrderNotFoundException("Order not found"));

        order.setStatus(CANCELLED);
        orderRepository.save(order);
    }
}