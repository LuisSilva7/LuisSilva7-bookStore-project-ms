package org.bookStore.order.aggregates;

import org.axonframework.eventsourcing.EventSourcingHandler;
import org.bookStore.common.commands.CancelOrderCommand;
import org.bookStore.common.events.OrderCancelledEvent;
import org.bookStore.order.commands.CreateOrderCommand;
import org.bookStore.order.commands.FinalizeOrderCommand;
import org.bookStore.order.commands.UpdateShippingOrderIdCommand;
import org.bookStore.order.events.OrderCreatedEvent;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.bookStore.order.events.OrderFinalizedEvent;
import org.bookStore.order.events.ShippingOrderIdUpdatedEvent;

@Slf4j
@NoArgsConstructor
@Aggregate
public class OrderAggregate {

    @AggregateIdentifier
    private String orderId;

    private Long userId;
    private String status; // Ex: PENDING, FINALIZED, CANCELLED

    @CommandHandler
    public OrderAggregate(CreateOrderCommand command) {
        log.info("Handling CreateOrderCommand for orderId={} userId={}", command.orderId(), command.userId());

        AggregateLifecycle.apply(new OrderCreatedEvent(
                command.orderId(),
                command.userId(),
                command.orderDetails(),
                command.firstName(),
                command.lastName(),
                command.address(),
                command.city(),
                command.email(),
                command.postalCode()
        ));
    }

    @EventSourcingHandler
    public void on(OrderCreatedEvent event) {
        log.info("OrderCreatedEvent applied: orderId={} userId={}", event.orderId(), event.userId());

        this.orderId = event.orderId();
        this.userId = event.userId();
        this.status = "PENDING";
    }

    @CommandHandler
    public void handle(UpdateShippingOrderIdCommand command) {
        log.info("[Aggregate] Received UpdateShippingOrderIdCommand for orderId={} with shippingOrderId={}",
                command.orderId(), command.shippingOrderId());

        AggregateLifecycle.apply(new ShippingOrderIdUpdatedEvent(
                command.orderId(),
                command.shippingOrderId()
        ));
    }

    @EventSourcingHandler
    public void on(ShippingOrderIdUpdatedEvent event) {
        log.info("[Aggregate] State updated with shippingOrderId={}", event.shippingOrderId());
    }

    @CommandHandler
    public void handle(CancelOrderCommand command) {
        log.info("[OrderAggregate] Received CancelOrderCommand for orderId={}", command.orderId());
        AggregateLifecycle.apply(new OrderCancelledEvent(command.orderId()));
    }

    @EventSourcingHandler
    public void on(OrderCancelledEvent event) {
        log.info("[OrderAggregate] Order cancelled: {}", event.orderId());
        this.status = "CANCELED";
    }

    @CommandHandler
    public void handle(FinalizeOrderCommand command) {
        AggregateLifecycle.apply(new OrderFinalizedEvent(command.orderId()));
    }

    @EventSourcingHandler
    public void on(OrderFinalizedEvent event) {
        this.status = "FINALIZED";
    }
}
