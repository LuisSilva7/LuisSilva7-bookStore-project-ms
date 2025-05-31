package org.bookStore.book.book;

import lombok.RequiredArgsConstructor;
import org.bookStore.book.author.AuthorRepository;
import org.bookStore.book.category.CategoryRepository;
import org.bookStore.book.exception.custom.AuthorNotFoundException;
import org.bookStore.book.exception.custom.BookNotFoundException;
import org.bookStore.book.exception.custom.CategoryNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final CategoryRepository categoryRepository;
    private final AuthorRepository authorRepository;

    public BookResponse createBook(CreateBookRequest request) {
        Book created = bookRepository.save(bookMapper.toBook(request));

        return bookMapper.toBookResponse(created);
    }

    public List<BookResponse> getAllBooks() {
        List<Book> books = bookRepository.findAll();

        return books.stream()
                .map(bookMapper::toBookResponse)
                .toList();
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

    public BookResponse updateBookQuantity(Long id, UpdateQuantityBookRequest request) {
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Cannot update quantity. Book not found with Id: " + id));

        existingBook.setQuantity(request.quantity());
        Book saved = bookRepository.save(existingBook);
        return bookMapper.toBookResponse(saved);
    }
}
