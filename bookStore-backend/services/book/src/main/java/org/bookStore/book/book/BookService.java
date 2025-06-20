package org.bookStore.book.book;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bookStore.book.author.AuthorRepository;
import org.bookStore.book.category.CategoryRepository;
import org.bookStore.book.exception.custom.AuthorNotFoundException;
import org.bookStore.book.exception.custom.BookNotFoundException;
import org.bookStore.book.exception.custom.CategoryNotFoundException;
import org.bookStore.book.exception.custom.OutOfStockException;
import org.bookStore.book.outbox.OutboxEventService;
import org.bookStore.book.response.PageResponse;
import org.bookStore.common.commands.UpdateBookQuantityCommand;
import org.bookStore.common.events.BookInfoEvent;
import org.bookStore.common.events.BookQuantityRollbackedEvent;
import org.bookStore.common.events.BookQuantityUpdatedEvent;
import org.bookStore.common.utils.CreateOrderDetailsRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final CategoryRepository categoryRepository;
    private final AuthorRepository authorRepository;
    private final OutboxEventService outboxEventService;

    public BookResponse createBook(CreateBookRequest request) {
        Book created = bookRepository.save(bookMapper.toBook(request));

        return bookMapper.toBookResponse(created);
    }

    public PageResponse<BookResponse> getAllBooks(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("title").ascending());
        Page<Book> books = bookRepository.findAll(pageable);

        List<BookResponse> response = books.stream()
                .map(bookMapper::toBookResponse)
                .toList();

        return new PageResponse<>(
                response,
                books.getNumber(),
                books.getSize(),
                books.getTotalElements(),
                books.getTotalPages(),
                books.isFirst(),
                books.isLast()
        );
    }

    public BookResponse getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book not found with Id: " + id));

        return bookMapper.toBookResponse(book);
    }

    public List<BookResponse> getBooksByCategoryId(Long categoryId) {
        boolean categoryExists = categoryRepository.existsById(categoryId);
        if (!categoryExists) {
            throw new CategoryNotFoundException("Category not found with Id: " + categoryId);
        }

        List<Book> books = bookRepository.findByCategory_Id(categoryId);

        return books.stream()
                .map(bookMapper::toBookResponse)
                .toList();
    }

    public List<BookResponse> getBooksByAuthorId(Long authorId) {
        boolean authorExists = authorRepository.existsById(authorId);
        if (!authorExists) {
            throw new AuthorNotFoundException("Author not found with Id: " + authorId);
        }

        List<Book> books = bookRepository.findAllByAuthor_Id(authorId);

        return books.stream()
                .map(bookMapper::toBookResponse)
                .toList();
    }

    public List<BookResponse> searchBooks(String query) {
        List<Book> results = bookRepository.searchBooks(query);

        return results.stream()
                .map(bookMapper::toBookResponse)
                .toList();
    }

    public BookResponse updateBookQuantity(Long id, UpdateBookQuantityRequest request) {
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Cannot update quantity. Book not found with Id: " + id));

        existingBook.setQuantity(request.quantity());
        Book saved = bookRepository.save(existingBook);
        return bookMapper.toBookResponse(saved);
    }

    @Transactional
    public void updateBookQuantities(UpdateBookQuantityCommand command) {
        for (CreateOrderDetailsRequest item : command.orderDetails()) {
            Book book = bookRepository.findById(item.bookId())
                    .orElseThrow(() -> new BookNotFoundException(
                            "Cannot update quantity. Book not found with Id: " + item.bookId()));

            int newQuantity = book.getQuantity() - item.quantity();
            if (newQuantity < 0) throw new OutOfStockException(
                    "Book with Id: " + item.bookId() + " is out of stock!");

            book.setQuantity(newQuantity);
            bookRepository.save(book);

            BookQuantityUpdatedEvent event = new BookQuantityUpdatedEvent(
                    command.orderId(),
                    command.userId(),
                    command.orderDetails()
            );

            outboxEventService.saveEvent(
                    event.orderId(),
                    BookQuantityUpdatedEvent.class.getSimpleName(),
                    event
            );

            log.info("BookQuantityUpdatedEvent saved to outbox for orderId={}", command.orderId());

            BookInfoEvent bookInfoEvent = new BookInfoEvent(
                    command.orderId(),
                    command.orderDetails()
            );

            outboxEventService.saveEvent(
                    bookInfoEvent.orderId(),
                    BookInfoEvent.class.getSimpleName(),
                    bookInfoEvent
            );

            log.info("BookInfoEvent saved to outbox for orderId={}", command.orderId());
        }
    }

    @Transactional
    public void incrementStock(Long bookId, int quantity, String orderId, Long userId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException("Book not found"));

        book.setQuantity(book.getQuantity() + quantity);
        bookRepository.save(book);

        log.info("Stock incremented for bookId={}, new quantity={}", bookId, book.getQuantity());

        BookQuantityRollbackedEvent event = new BookQuantityRollbackedEvent(
                orderId,
                userId
        );

        outboxEventService.saveEvent(event.orderId(), BookQuantityRollbackedEvent.class.getSimpleName(), event);
    }
}
