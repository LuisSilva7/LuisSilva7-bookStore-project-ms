package org.bookStore.shipping.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bookStore.common.commands.CreateShippingOrderCommand;
import org.bookStore.common.events.ShippingInfoEvent;
import org.bookStore.common.events.ShippingOrderCreatedEvent;
import org.bookStore.common.events.ShippingOrderCreationFailedEvent;
import org.bookStore.shipping.outbox.OutboxEventService;
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
    private final ObjectMapper objectMapper;
    private final OutboxEventService outboxEventService;

    @KafkaListener(topics = "create-shipping-order", groupId = "shipping")
    public void handleCreateShippingOrder(String payload) throws JsonProcessingException {
        CreateShippingOrderCommand command = objectMapper.readValue(payload, CreateShippingOrderCommand.class);

        log.info("Received CreateShippingOrderCommand for orderId={}", command.orderId());

        try {
            ShippingOrder saved = shippingOrderService.createShippingOrder(command);

            // mudar isto do cqrs
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

            outboxEventService.saveEvent(
                    command.orderId(),
                    ShippingOrderCreationFailedEvent.class.getSimpleName(),
                    failedEvent
            );

            log.info("ShippingOrderCreationFailedEvent saved to outbox for orderId={}", command.orderId());
        }

    }
}