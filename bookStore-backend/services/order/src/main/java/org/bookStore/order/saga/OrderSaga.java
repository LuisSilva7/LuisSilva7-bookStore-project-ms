package org.bookStore.order.saga;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.bookStore.common.commands.ClearCartCommand;
import org.bookStore.common.commands.CreateShippingOrderCommand;
import org.bookStore.common.commands.RollbackBookQuantityCommand;
import org.bookStore.common.commands.UpdateBookQuantityCommand;
import org.bookStore.common.utils.CreateOrderDetailsRequest;
import org.bookStore.common.events.*;
import org.bookStore.order.commands.CancelOrderCommand;
import org.bookStore.order.commands.FinalizeOrderCommand;
import org.bookStore.order.commands.UpdateShippingOrderIdCommand;
import org.bookStore.order.events.InitializeSagaEvent;
import lombok.extern.slf4j.Slf4j;
import org.bookStore.order.events.ShippingOrderIdUpdatedEvent;
import org.bookStore.order.outbox.OutboxEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;

import java.time.LocalDateTime;
import java.util.List;

import static org.bookStore.common.utils.OrderStatus.FINALIZED;
import static org.bookStore.common.utils.OrderStatus.PENDING;

@Saga
@Slf4j
public class OrderSaga {

    @Autowired
    private transient KafkaTemplate<String, Object> kafkaTemplate;
    @Autowired
    private transient CommandGateway commandGateway;
    @Autowired
    private transient OutboxEventService outboxEventService;

    @StartSaga
    @SagaEventHandler(associationProperty = "orderId")
    public void handle(InitializeSagaEvent event) {
        log.info("SAGA started for orderId={}", event.orderId());

        CreateShippingOrderCommand shippingCommand = new CreateShippingOrderCommand(
                event.orderId(),
                event.userId(),
                event.firstName(),
                event.lastName(),
                event.address(),
                event.city(),
                event.email(),
                event.postalCode(),
                event.orderDetails()
        );

        outboxEventService.saveEvent(
                event.orderId(),
                CreateShippingOrderCommand.class.getSimpleName(),
                shippingCommand
        );
        log.info("Command sent to Outbox: CreateShippingOrderCommand");

        // mudar isto do cqrs
        double totalPrice = event.orderDetails().stream()
                .mapToDouble(d -> d.unitPrice() * d.quantity())
                .sum();

        OrderInfoEvent queryEvent = new OrderInfoEvent(
                event.orderId(),
                LocalDateTime.now(),
                totalPrice,
                PENDING,
                event.userId()
        );

        kafkaTemplate.send("order-events-1", event.orderId(), queryEvent);
        log.info("Event sent to Kafka: OrderQueryUpdateEvent");
    }

    @SagaEventHandler(associationProperty = "orderId")
    public void on(ShippingOrderCreatedEvent event) {
        log.info("[SAGA] Received ShippingOrderCreatedEvent for orderId={} with shippingOrderId={}",
                event.orderId(), event.shippingOrderId());

        UpdateShippingOrderIdCommand updateCommand = new UpdateShippingOrderIdCommand(
                event.orderId(),
                event.shippingOrderId(),
                event.userId(),
                event.orderDetails()
        );

        commandGateway.send(updateCommand);
        log.info("Sent command: UpdateShippingOrderIdCommand");
    }

    @SagaEventHandler(associationProperty = "orderId")
    public void on(ShippingOrderIdUpdatedEvent event) {
        log.info("[SAGA] Received ShippingOrderIdUpdatedEvent for orderId={}", event.orderId());

        List<CreateOrderDetailsRequest> books = event.orderDetails().stream()
                .map(detail -> new CreateOrderDetailsRequest(
                        detail.quantity(),
                        detail.unitPrice(),
                        detail.bookId()
                ))
                .toList();

        UpdateBookQuantityCommand bookCommand = new UpdateBookQuantityCommand(
                event.orderId(),
                event.userId(),
                books
        );

        outboxEventService.saveEvent(
                event.orderId(),
                UpdateBookQuantityCommand.class.getSimpleName(),
                bookCommand
        );

        log.info("Command sent to Outbox: UpdateBookQuantityCommand");
    }

    @SagaEventHandler(associationProperty = "orderId")
    public void on(BookQuantityUpdatedEvent event) {
        log.info("[SAGA] Received BookQuantityUpdatedEvent for orderId={}", event.orderId());

        ClearCartCommand command = new ClearCartCommand(
                event.orderId(),
                event.userId(),
                event.orderDetails()
        );

        outboxEventService.saveEvent(
                event.orderId(),
                ClearCartCommand.class.getSimpleName(),
                command
        );

        log.info("Command sent to Outbox: ClearCartCommand");
    }

    @SagaEventHandler(associationProperty = "orderId")
    @EndSaga
    public void on(CartClearedEvent event) {
        log.info("[SAGA] Successfully completed for orderId={}", event.orderId());

        commandGateway.send(new FinalizeOrderCommand(event.orderId()));

        OrderInfoFinalEvent queryEvent = new OrderInfoFinalEvent(
                event.orderId(),
                FINALIZED
        );

        // mudar cqrs
        kafkaTemplate.send("order-events-4", event.orderId(), queryEvent);
        log.info("Event sent to Kafka: OrderInfoFinalEvent");
    }

    @SagaEventHandler(associationProperty = "orderId")
    public void on(ShippingOrderCreationFailedEvent event) {
        log.info("[SAGA] Shipping order creation failed for orderId={} → sending CancelOrderCommand",
                event.orderId());

        commandGateway.send(new CancelOrderCommand(event.orderId()));
    }

    @SagaEventHandler(associationProperty = "orderId")
    public void on(BookQuantityUpdateFailedEvent event) {
        log.info("[SAGA] Book quantity update failed for orderId={}, sending CancelOrderCommand", event.orderId());

        commandGateway.send(new CancelOrderCommand(event.orderId()));
    }

    @SagaEventHandler(associationProperty = "orderId")
    public void on(CartClearFailedEvent event) {
        log.warn("[SAGA] Failed to clear cart for orderId={}", event.orderId());

        RollbackBookQuantityCommand rollbackCommand = new RollbackBookQuantityCommand(
                event.orderId(),
                event.userId(),
                event.books()
        );
        kafkaTemplate.send("rollback-book-quantity", event.orderId(), rollbackCommand);

        outboxEventService.saveEvent(
                event.orderId(),
                RollbackBookQuantityCommand.class.getSimpleName(),
                rollbackCommand
        );

        log.info("Command sent to Outbox: RollbackBookQuantityCommand");
    }

    @SagaEventHandler(associationProperty = "orderId")
    public void on(BookQuantityRollbackedEvent event) {
        log.info("[SAGA] Book quantity rollbacked for orderId={}, sending CancelOrderCommand", event.orderId());

        commandGateway.send(new CancelOrderCommand(event.orderId()));
    }
}
