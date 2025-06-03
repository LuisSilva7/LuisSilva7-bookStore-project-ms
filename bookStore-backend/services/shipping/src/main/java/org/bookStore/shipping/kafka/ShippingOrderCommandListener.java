package org.bookStore.shipping.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bookStore.common.commands.CreateShippingOrderCommand;
import org.bookStore.common.events.ShippingInfoEvent;
import org.bookStore.common.events.ShippingOrderCreatedEvent;
import org.bookStore.common.events.ShippingOrderCreationFailedEvent;
import org.bookStore.shipping.shipping.ShippingOrder;
import org.bookStore.shipping.shipping.ShippingOrderService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShippingOrderCommandListener {

    private final ShippingOrderService shippingOrderService;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @KafkaListener(topics = "create-shipping-order", groupId = "shipping")
    public void handleCreateShippingOrder(CreateShippingOrderCommand command) {
        try {
            log.info("Received CreateShippingOrderCommand for orderId={}", command.orderId());

            ShippingOrder saved = shippingOrderService.createShippingOrder(command);

            ShippingOrderCreatedEvent event = new ShippingOrderCreatedEvent(
                    command.orderId(),
                    command.userId(),
                    saved.getId(),
                    command.orderDetails()
            );

            kafkaTemplate.send("shipping-order-created", command.orderId(), event);
            log.info("ShippingOrderCreatedEvent sent");

            ShippingInfoEvent shippingInfo = new ShippingInfoEvent(
                    command.orderId(),
                    command.firstName(),
                    command.address(),
                    command.city(),
                    command.postalCode()
            );

            kafkaTemplate.send("order-events-2", command.orderId(), shippingInfo);
            log.info("ShippingInfoUpdatedEvent sent to order-events");

        } catch (Exception e) {
            log.error("Error creating ShippingOrder for orderId={}: {}", command.orderId(), e.getMessage());

            ShippingOrderCreationFailedEvent failedEvent = new ShippingOrderCreationFailedEvent(
                    command.orderId(),
                    command.userId()
            );

            kafkaTemplate.send("shipping-order-creation-failed", command.orderId(), failedEvent);
            log.info("ShippingOrderCreationFailedEvent sent for orderId={}", command.orderId());
        }
    }
}