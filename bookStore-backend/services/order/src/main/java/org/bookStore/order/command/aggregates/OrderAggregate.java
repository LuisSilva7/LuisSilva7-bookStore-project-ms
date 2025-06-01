package org.bookStore.order.command.aggregates;

import org.axonframework.eventsourcing.EventSourcingHandler;
import org.bookStore.order.command.commands.CreateOrderCommand;
import org.bookStore.order.command.commands.FinalizeOrderCommand;
import org.bookStore.order.command.events.OrderCreatedEvent;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Slf4j
@NoArgsConstructor
@Aggregate
public class OrderAggregate {

    @AggregateIdentifier
    private String orderId;

    private String userId;
    private String status;

    @CommandHandler
    public OrderAggregate(CreateOrderCommand command) {
        log.info("Handling CreateOrderCommand for orderId={} userId={}", command.orderId(), command.userId());
        AggregateLifecycle.apply(new OrderCreatedEvent(command.orderId(), command.userId()));
    }

    @EventSourcingHandler
    public void on(OrderCreatedEvent event) {
        log.info("OrderCreatedEvent applied: orderId={} userId={}", event.orderId(), event.userId());
        this.orderId = event.orderId();
        this.userId = event.userId();
        this.status = "PENDING";
    }

    @CommandHandler
    public void handle(FinalizeOrderCommand command) {
        log.info("✅ Finalizar ordem: {}", command.getOrderId());
        this.status = "FINALIZED";  // ou enum, se estiveres a usar
    }
}
