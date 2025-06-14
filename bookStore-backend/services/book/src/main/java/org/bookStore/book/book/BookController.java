package org.bookStore.book.book;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.bookStore.book.response.ApiResponse;
import org.bookStore.book.response.PageResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping
    public ResponseEntity<ApiResponse<BookResponse>> createBook(@RequestBody @Valid CreateBookRequest request) {
        BookResponse createdBook = bookService.createBook(request);

        return ResponseEntity.ok(new ApiResponse<>(
                "Book created successfully!", createdBook));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<BookResponse>>> getAllBooks(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size
    ) {
        PageResponse<BookResponse> response = bookService.getAllBooks(page, size);

        return ResponseEntity.ok(new ApiResponse<>(
                "Books obtained successfully!", response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<BookResponse>> getBookById(@PathVariable("id") Long bookId) {
        BookResponse book = bookService.getBookById(bookId);

        return ResponseEntity.ok(new ApiResponse<>(
                "Book with id: " + bookId + " obtained successfully!", book));
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<ApiResponse<List<BookResponse>>> getBooksByCategoryId(@PathVariable("id") Long categoryId) {
        List<BookResponse> books = bookService.getBooksByCategoryId(categoryId);

        return ResponseEntity.ok(new ApiResponse<>(
                "Books with category id: " + categoryId + " obtained successfully!", books));
    }

    @GetMapping("/author/{id}")
    public ResponseEntity<ApiResponse<List<BookResponse>>> getBooksByAuthor(@PathVariable("id") Long authorId) {
        List<BookResponse> books = bookService.getBooksByAuthorId(authorId);

        return ResponseEntity.ok(new ApiResponse<>(
                "Books with author id: " + authorId + " obtained successfully!", books));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<BookResponse>>> searchBooks(@RequestParam("query") String query) {
        List<BookResponse> results = bookService.searchBooks(query);

        return ResponseEntity.ok(new ApiResponse<>(
                "Books with query: " + query + " obtained successfully!", results));
    }

    @PutMapping("/quantity/{id}")
    public ResponseEntity<ApiResponse<BookResponse>> updateBookQuantity(@PathVariable("id") Long bookId,
                                                   @RequestBody @Valid UpdateBookQuantityRequest request) {
        BookResponse updatedBook = bookService.updateBookQuantity(bookId, request);

        return ResponseEntity.ok(new ApiResponse<>(
                "Book with id: " + bookId + " updated successfully!", updatedBook));
    }
}
