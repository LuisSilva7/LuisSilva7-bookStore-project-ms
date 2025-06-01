package org.bookStore.order.kafka.cart;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bookStore.order.command.commands.ClearCartCommand;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaCartProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendClearCartCommand(ClearCartCommand command) {
        log.info("🧺 [Cart] Enviando comando para limpar carrinho: {}", command);
        kafkaTemplate.send("cart-clear", command);
    }
}