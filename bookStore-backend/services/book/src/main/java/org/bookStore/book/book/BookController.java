package org.bookStore.book.book;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping("/books")
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = bookService.getAllBooks();

        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<Book> getBookByID(@PathVariable Long bookID) {

        Book existBook = bookService.getBookByID(bookID);

        if(existBook != null) {
            return new ResponseEntity<>(existBook, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/books/category/{id}")
    public ResponseEntity<List<Book>> getBooksByCategoryID(@PathVariable Long categoryID) {

        List<Book> existBook = bookService.getBooksByCategoryID(categoryID);

        if(existBook != null){
            return new ResponseEntity<>(existBook, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/update-quantity/{id}")
    public ResponseEntity<Book> updateBookQuantity(@PathVariable Long bookID , @RequestBody Book book){

        Book updatedBookQuantity = bookService.updateBookQuantity(bookID, book);

        return new ResponseEntity<>(updatedBookQuantity, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Book>> searchBooks(@RequestParam("query") String query) {
        List<Book> searchResults = bookService.searchBooks(query);

        if (searchResults != null && !searchResults.isEmpty()) {
            return new ResponseEntity<>(searchResults, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
