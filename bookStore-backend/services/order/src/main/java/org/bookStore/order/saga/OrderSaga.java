package org.bookStore.order.saga;

import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.SagaLifecycle;
import org.axonframework.spring.stereotype.Saga;

import jakarta.inject.Inject;
import org.bookStore.order.command.commands.ClearCartCommand;
import org.bookStore.order.command.commands.CreateShippingOrderCommand;
import org.bookStore.order.command.commands.UpdateBookQuantityCommand;
import org.bookStore.order.command.events.BookQuantityUpdatedEvent;
import org.bookStore.order.command.events.CartClearedEvent;
import org.bookStore.order.command.events.OrderCreatedEvent;
import org.bookStore.order.command.events.ShippingOrderCreatedEvent;

@Slf4j
@Saga
public class OrderSaga {

    @Inject
    private transient CommandGateway commandGateway;

    @SagaEventHandler(associationProperty = "orderId")
    public void on(OrderCreatedEvent event) {
        log.info("[SAGA] 🛒 OrderCreatedEvent recebido → Criar Shipment");

        CreateShippingOrderCommand command = new CreateShippingOrderCommand(
                event.orderId(),
                event.userId()
        );
        commandGateway.send(command);
    }

    @SagaEventHandler(associationProperty = "orderId")
    public void on(ShippingOrderCreatedEvent event) {
        log.info("[SAGA] 📦 ShipmentCreatedEvent recebido → Limpar Carrinho");

        ClearCartCommand command = new ClearCartCommand(
                event.userId(),
                event.orderId()
        );
        commandGateway.send(command);
    }

    @SagaEventHandler(associationProperty = "orderId")
    public void on(CartClearedEvent event) {
        log.info("[SAGA] 🧹 CartClearedEvent recebido → Atualizar Stock");

        UpdateBookQuantityCommand command = new UpdateBookQuantityCommand(
                event.orderId()
        );
        commandGateway.send(command);
    }

    @SagaEventHandler(associationProperty = "orderId")
    public void on(BookQuantityUpdatedEvent event) {
        log.info("[SAGA] ✅ BookStockUpdatedEvent recebido → Terminar SAGA");

        // Podes atualizar o estado da encomenda aqui se quiseres
        SagaLifecycle.end();
    }
}
