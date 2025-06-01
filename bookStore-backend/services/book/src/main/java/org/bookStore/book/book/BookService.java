package org.bookStore.book.book;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bookStore.book.author.AuthorRepository;
import org.bookStore.book.category.CategoryRepository;
import org.bookStore.book.exception.custom.AuthorNotFoundException;
import org.bookStore.book.exception.custom.BookNotFoundException;
import org.bookStore.book.exception.custom.CategoryNotFoundException;
import org.bookStore.book.kafka.BookQuantityUpdatedEvent;
import org.bookStore.book.response.PageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final CategoryRepository categoryRepository;
    private final AuthorRepository authorRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;

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

    public void decrementStockForOrder(String orderId) {
        // ⚠️ Aqui vais buscar os livros associados à ordem e atualizar o stock de cada um
        // Por agora, exemplo simples:
        Book book = bookRepository.findById(1L).orElseThrow(); // substituir por lógica real
        book.setQuantity(book.getQuantity() - 1);
        bookRepository.save(book);

        log.info("📘 Stock do livro atualizado. OrderId: {}", orderId);

        BookQuantityUpdatedEvent event = new BookQuantityUpdatedEvent(orderId, "user-id");
        log.info("📤 [Book] Enviando BookStockUpdatedEvent para Kafka: {}", event);
        kafkaTemplate.send("book-updated", event);
    }
}
