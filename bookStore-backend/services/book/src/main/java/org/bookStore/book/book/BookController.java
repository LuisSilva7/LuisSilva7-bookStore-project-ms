package org.bookStore.book.book;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        Book savedBook = bookService.createBook(book);
        return new ResponseEntity<>(savedBook, CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = bookService.getAllBooks();
        return ResponseEntity.ok(books);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable("id") Long id) {
        Book book = bookService.getBookById(id);
        return (book != null)
                ? ResponseEntity.ok(book)
                : ResponseEntity.notFound().build();
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<List<Book>> getBooksByCategoryId(@PathVariable("id") Long categoryId) {
        List<Book> books = bookService.getBooksByCategoryId(categoryId);
        return ResponseEntity.ok(books);
    }

    @PutMapping("/update-quantity/{id}")
    public ResponseEntity<Book> updateBookQuantity(@PathVariable("id") Long id, @RequestBody Book book) {
        Book updated = bookService.updateBookQuantity(id, book);

        return (updated != null)
                ? ResponseEntity.ok(updated)
                : ResponseEntity.notFound().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<Book>> searchBooks(@RequestParam("query") String query) {
        List<Book> results = bookService.searchBooks(query);
        return ResponseEntity.ok(results);
    }

    @GetMapping("/author/{id}")
    public ResponseEntity<List<Book>> getBooksByAuthor(@PathVariable("id") Long authorId) {
        List<Book> books = bookService.getBooksByAuthorId(authorId);
        return ResponseEntity.ok(books);
    }
}
