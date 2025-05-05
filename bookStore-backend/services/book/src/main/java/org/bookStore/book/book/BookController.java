package org.bookStore.book.book;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookService bookSerivce;

    @GetMapping("/books")
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = bookSerivce.getAllBooks();

        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<Book> getBookByID(@PathVariable Long bookID) {

        Book existBook = bookSerivce.getBookByID(bookID);

        if(existBook != null) {
            return new ResponseEntity<>(existBook, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/books/category/{id}")
    public ResponseEntity<List<Book>> getBooksByCategoryID(@PathVariable Long categoryID) {

        List<Book> existBook = bookSerivce.getBooksByCategoryID(categoryID);

        if(existBook != null){
            return new ResponseEntity<>(existBook, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/update-quantity/{id}")
    public ResponseEntity<Book> updateBookQuantity(@PathVariable Long bookID , @RequestBody Book book){

        Book updatedBookQuantity = bookSerivce.updateBookQuantity(bookID, book);

        return new ResponseEntity<>(updatedBookQuantity, HttpStatus.OK);
    }
}
