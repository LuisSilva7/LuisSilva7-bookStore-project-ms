package org.bookStore.order.kafka.shipping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bookStore.order.command.commands.CreateShippingOrderCommand;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaShippingProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendCreateShippingCommand(CreateShippingOrderCommand command) {
        log.info("📤 Enviando mensagem para tópico shipping-create: {}", command);
        kafkaTemplate.send("shipping-create", command);
    }
}
