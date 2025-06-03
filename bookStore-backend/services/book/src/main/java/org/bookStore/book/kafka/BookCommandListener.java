package org.bookStore.book.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bookStore.book.book.BookService;
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

    @KafkaListener(topics = "update-book-quantity", groupId = "book")
    public void handleUpdateBookQuantity(UpdateBookQuantityCommand command) {
        try {
            log.info("Received UpdateBookQuantityCommand for orderId={}", command.orderId());

            bookService.updateBookQuantities(command.orderDetails());

            BookQuantityUpdatedEvent event = new BookQuantityUpdatedEvent(
                    command.orderId(),
                    command.userId(),
                    command.orderDetails()
            );

            kafkaTemplate.send("book-quantity-updated", command.orderId(), event);
            log.info("BookQuantityUpdatedEvent sent for orderId={}", command.orderId());

            BookInfoEvent bookInfoEvent = new BookInfoEvent(
                    command.orderId(),
                    command.orderDetails()
            );

            kafkaTemplate.send("order-events-3", command.orderId(), bookInfoEvent);
            log.info("BookQuantityUpdatedEvent sent to order-events for query update");

        } catch (Exception e) {
            log.error("Error updating stock for orderId={}: {}", command.orderId(), e.getMessage());

            BookQuantityUpdateFailedEvent failedEvent = new BookQuantityUpdateFailedEvent(
                    command.orderId(),
                    command.userId()
            );

            kafkaTemplate.send("book-quantity-update-failed", command.orderId(), failedEvent);
            log.info("BookQuantityUpdateFailedEvent sent for orderId={}", command.orderId());
        }
    }

    @KafkaListener(topics = "rollback-book-quantity", groupId = "book")
    public void handleRollbackBookQuantity(RollbackBookQuantityCommand command) {
        log.info("[BookService] Reverting quantities for orderId={}", command.orderId());

        command.books().forEach(book -> {
            bookService.incrementStock(book.bookId(), book.quantity());
        });

        BookQuantityRollbackedEvent event = new BookQuantityRollbackedEvent(
                command.orderId(),
                command.userId(),
                command.books()
        );

        kafkaTemplate.send("book-quantity-rollbacked", command.orderId(), event);
        log.info("BookQuantityRollbackedEvent sent for orderId={}", command.orderId());
    }
}
