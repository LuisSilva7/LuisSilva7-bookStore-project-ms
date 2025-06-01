package org.bookStore.order.handlers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.bookStore.order.command.commands.CreateShippingOrderCommand;
import org.bookStore.order.kafka.shipping.KafkaShippingProducer;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ShippingCommandHandler {

    private final KafkaShippingProducer kafkaShippingProducer;

    @CommandHandler
    public void handle(CreateShippingOrderCommand command) {
        log.info("📦 Enviar comando CreateShippingOrderCommand via Kafka...");
        kafkaShippingProducer.sendCreateShippingCommand(command);
    }
}
