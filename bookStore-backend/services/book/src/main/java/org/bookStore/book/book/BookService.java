package org.bookStore.book.book;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public List<Book> getAllBooks(){
        return bookRepository.findAll();
    }

    public Book getBookByID(Long bookID){
        return bookRepository.findById(bookID).orElse(null);
    }

    public List<Book> getBooksByCategoryID(Long categoryID){
        return bookRepository.findByCategoryId(categoryID);
    }

    public List<Book> searchBooks(String query) {
        return bookRepository.searchBooks(query);
    }

    public Book updateBookQuantity(Long bookID, Book book){

        Book existBook = bookRepository.findById(bookID).orElse(null);

        if(existBook != null) {
            existBook.setQuantity(book.getQuantity());
            bookRepository.save(existBook);

            return existBook;
        } else {
            return null;
        }
    }
}
