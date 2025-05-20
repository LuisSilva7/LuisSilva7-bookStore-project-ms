package org.bookStore.book.book;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public Book createBook(Book book) {
        return bookRepository.save(book);
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Book not found with Id: " + id));
    }

    public List<Book> getBooksByCategoryId(Long categoryId) {
        return bookRepository.findByCategoryId(categoryId);
    }

    public Book updateBookQuantity(Long id, Book bookData) {
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Cannot update quantity. Book not found with Id: " + id));

        existingBook.setQuantity(bookData.getQuantity());
        return bookRepository.save(existingBook);
    }

    public List<Book> searchBooks(String query) {
        return bookRepository.searchBooks(query);
    }

    public List<Book> getBooksByAuthorId(Long authorId) {
        return bookRepository.findAllBooksByAuthorId(authorId);
    }
}
