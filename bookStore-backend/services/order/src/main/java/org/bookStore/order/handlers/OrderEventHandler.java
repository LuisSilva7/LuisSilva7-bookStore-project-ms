package org.bookStore.order.handlers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.bookStore.common.commands.RollbackBookQuantityCommand;
import org.bookStore.common.events.CartClearFailedEvent;
import org.bookStore.common.events.OrderCancelledEvent;
import org.bookStore.order.events.OrderCreatedEvent;
import org.bookStore.order.events.OrderFinalizedEvent;
import org.bookStore.order.events.ShippingOrderIdUpdatedEvent;
import org.bookStore.order.exception.custom.OrderNotFoundException;
import org.bookStore.order.order.Order;
import org.bookStore.order.order.OrderMapper;
import org.bookStore.order.order.OrderRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import static org.bookStore.order.order.OrderStatus.CANCELLED;
import static org.bookStore.order.order.OrderStatus.FINALIZED;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderEventHandler {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @EventHandler
    public void on(OrderCreatedEvent event) {
        Order order = orderMapper.toOrder(event);
        orderRepository.save(order);
    }

    @EventHandler
    public void on(ShippingOrderIdUpdatedEvent event) {
        Order order = orderRepository.findById(event.orderId())
                .orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + event.orderId()));

        log.info("Order being updated with shippingOrderId={} in the database", order);

        order.setShippingOrderId(event.shippingOrderId());
        orderRepository.save(order);

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
        log.info("[ReadModel] Order status updated to CANCELED");
    }
}