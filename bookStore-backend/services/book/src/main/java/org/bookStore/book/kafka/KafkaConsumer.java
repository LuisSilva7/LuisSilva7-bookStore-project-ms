package org.bookStore.book.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bookStore.book.book.BookService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaConsumer {

    private final BookService bookService;

    @KafkaListener(topics = "book-decrement", groupId = "book-group")
    public void handleDecrementCommand(UpdateBookQuantityCommand command) {
        log.info("📥 [Book] Recebido comando para decrementar stock: {}", command);

        // Simular lógica de decremento
        bookService.decrementStockForOrder(command.getOrderId());

        log.info("✅ [Book] Stock atualizado com sucesso.");
    }
}
