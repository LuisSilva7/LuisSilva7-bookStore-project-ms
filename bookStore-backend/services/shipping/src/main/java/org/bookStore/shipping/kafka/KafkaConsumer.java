package org.bookStore.shipping.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bookStore.shipping.shipping.ShippingOrderService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaConsumer {

    private final ShippingOrderService shippingOrderService;

    @KafkaListener(topics = "shipping-create", groupId = "shipping-group")
    public void consume(CreateShippingOrderCommand command) {
        log.info("📥 [Kafka] Recebido comando para criar shipment: {}", command);
        shippingOrderService.createShippingOrder(command.getOrderId(), command.getUserId());
    }
}
