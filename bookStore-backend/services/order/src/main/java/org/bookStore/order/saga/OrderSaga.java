package org.bookStore.order.saga;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.SagaLifecycle;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.bookStore.common.commands.*;
import org.bookStore.common.dto.CreateOrderDetailsRequest;
import org.bookStore.common.events.*;
import org.bookStore.order.command.commands.FinalizeOrderCommand;
import org.bookStore.order.command.commands.UpdateShippingOrderIdCommand;
import org.bookStore.order.command.events.OrderCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.List;

@Saga
@Slf4j
public class OrderSaga {

    @Autowired
    private transient KafkaTemplate<String, Object> kafkaTemplate;
    @Autowired
    private transient CommandGateway commandGateway;

    @StartSaga
    @SagaEventHandler(associationProperty = "orderId")
    public void handle(OrderCreatedEvent event) {
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

        kafkaTemplate.send("create-shipping-order", event.orderId(), shippingCommand);
        log.info("Command sent to Kafka: CreateShippingOrderCommand");
    }

    @SagaEventHandler(associationProperty = "orderId")
    public void on(ShippingOrderCreatedEvent event) {
        log.info("[SAGA] Received ShippingOrderCreatedEvent for orderId={} with shippingOrderId={}",
                event.orderId(), event.shippingOrderId());

        UpdateShippingOrderIdCommand updateCommand = new UpdateShippingOrderIdCommand(
                event.orderId(),
                event.shippingOrderId()
        );

        commandGateway.send(updateCommand);
        log.info("Sent command: UpdateShippingOrderIdCommand");

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

        kafkaTemplate.send("update-book-quantity", event.orderId(), bookCommand);
        log.info("Command sent to Kafka: UpdateBookQuantityCommand");
    }

    @SagaEventHandler(associationProperty = "orderId")
    public void on(BookQuantityUpdatedEvent event) {
        log.info("[SAGA] Received BookQuantityUpdatedEvent for orderId={}", event.orderId());

        ClearCartCommand command = new ClearCartCommand(event.orderId(), event.userId(), event.orderDetails());

        kafkaTemplate.send("clear-cart", event.orderId(), command);
        log.info("Sent ClearCartCommand for orderId={}", event.orderId());
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

        CancelOrderCommand cancelCommand = new CancelOrderCommand(event.orderId());
        commandGateway.send(cancelCommand);

        SagaLifecycle.end();
    }

    @SagaEventHandler(associationProperty = "orderId")
    public void on(BookQuantityUpdateFailedEvent event) {
        log.info("[SAGA] Failed to update stock, starting compensation");

        commandGateway.send(new CancelOrderCommand(event.orderId()));

        SagaLifecycle.end();
    }

    @SagaEventHandler(associationProperty = "orderId")
    public void on(ShippingOrderCreationFailedEvent event) {
        log.info("[SAGA] Shipping order creation failed → cancelling order");

        commandGateway.send(new CancelOrderCommand(event.orderId()));
        SagaLifecycle.end();
    }

    @SagaEventHandler(associationProperty = "orderId")
    @EndSaga
    public void on(CartClearedEvent event) {
        log.info("[SAGA] Successfully completed for orderId={}", event.orderId());

        commandGateway.send(new FinalizeOrderCommand(event.orderId()));
    }
}
