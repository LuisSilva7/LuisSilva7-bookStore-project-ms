package org.bookStore.order.kafka.book;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bookStore.order.command.commands.UpdateBookQuantityCommand;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaBookProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendDecrementStockCommand(UpdateBookQuantityCommand command) {
        log.info("📤 Enviando DecrementBookStockCommand via Kafka: {}", command);
        kafkaTemplate.send("book-decrement", command);
    }
}