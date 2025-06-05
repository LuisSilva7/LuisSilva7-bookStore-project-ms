package org.bookStore.book.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bookStore.book.book.BookService;
import org.bookStore.book.outbox.OutboxEventService;
import org.bookStore.common.commands.RollbackBookQuantityCommand;
import org.bookStore.common.commands.UpdateBookQuantityCommand;
import org.bookStore.common.events.*;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookCommandListener {

    private final BookService bookService;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private final OutboxEventService outboxEventService;

    @KafkaListener(topics = "update-book-quantity", groupId = "book")
    public void handleUpdateBookQuantity(String payload) throws JsonProcessingException {
        UpdateBookQuantityCommand command = objectMapper.readValue(payload, UpdateBookQuantityCommand.class);

        log.info("Received UpdateBookQuantityCommand for orderId={}", command.orderId());

        try {
            bookService.updateBookQuantities(command);

        } catch (Exception e) {
            log.error("Error updating stock for orderId={}: {}", command.orderId(), e.getMessage());

            BookQuantityUpdateFailedEvent failedEvent = new BookQuantityUpdateFailedEvent(
                    command.orderId(),
                    command.userId()
            );

            outboxEventService.saveEvent(
                    command.orderId(),
                    BookQuantityUpdateFailedEvent.class.getSimpleName(),
                    failedEvent
            );

            log.info("BookQuantityUpdateFailedEvent saved to outbox for orderId={}", command.orderId());
        }
    }

    @KafkaListener(topics = "rollback-book-quantity", groupId = "book")
    public void handleRollbackBookQuantity(RollbackBookQuantityCommand command) {
        log.info("[BookService] Reverting quantities for orderId={}", command.orderId());

        command.books().forEach(book -> {
            bookService.incrementStock(book.bookId(), book.quantity(), command.orderId(), command.userId());
        });

        log.info("BookQuantityRollbackedEvent sent for orderId={}", command.orderId());
    }
}
