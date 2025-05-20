package org.bookStore.book.defaultData;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.bookStore.book.author.Author;
import org.bookStore.book.author.AuthorRepository;
import org.bookStore.book.book.Book;
import org.bookStore.book.book.BookRepository;
import org.bookStore.book.category.Category;
import org.bookStore.book.category.CategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class DefaultBookData implements CommandLineRunner {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;

    @Override
    @Transactional
    public void run(String... args) {

        if (bookRepository.count() == 0) {

            Author luis = authorRepository.findByName("Luís Silva").orElseThrow();
            Author mariana = authorRepository.findByName("Mariana Carneiro").orElseThrow();

            Category tecnologia = categoryRepository.findByName("Tecnologia").orElseThrow();
            Category literatura = categoryRepository.findByName("Literatura").orElseThrow();

            List<Book> books = List.of(
                    Book.builder()
                            .title("Java Essencial")
                            .isbnNumber("9780000000001")
                            .description("Livro introdutório sobre Java.")
                            .price(24.99)
                            .quantity(10)
                            .author(luis)
                            .category(tecnologia)
                            .build(),

                    Book.builder()
                            .title("Redes de Computadores")
                            .isbnNumber("9780000000002")
                            .description("Fundamentos de redes e comunicações.")
                            .price(29.99)
                            .quantity(7)
                            .author(mariana)
                            .category(tecnologia)
                            .build(),

                    Book.builder()
                            .title("Romance do Século")
                            .isbnNumber("9780000000003")
                            .description("Um romance dramático inesquecível.")
                            .price(19.99)
                            .quantity(15)
                            .author(mariana)
                            .category(literatura)
                            .build()
            );

            bookRepository.saveAll(books);

            System.out.println("Default books were created!");
        } else {
            System.out.println("Default books already exist!");
        }
    }
}
