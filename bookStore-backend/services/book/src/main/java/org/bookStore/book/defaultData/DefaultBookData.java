package org.bookStore.book.defaultData;

import lombok.RequiredArgsConstructor;
import org.bookStore.book.author.Author;
import org.bookStore.book.author.AuthorRepository;
import org.bookStore.book.book.Book;
import org.bookStore.book.book.BookRepository;
import org.bookStore.book.category.Category;
import org.bookStore.book.category.CategoryRepository;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class DefaultBookData {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;

    public void bookData() {

        if (bookRepository.count() == 0) {

            Author luis = authorRepository.findByName("Luís Silva").orElseThrow();
            Author mariana = authorRepository.findByName("Mariana Carneiro").orElseThrow();
            Author joseN = authorRepository.findByName("José Novais").orElseThrow();
            Author joseQ = authorRepository.findByName("José Queirós").orElseThrow();

            Category tecnologia = categoryRepository.findByName("Tecnologia").orElseThrow();
            Category literatura = categoryRepository.findByName("Literatura").orElseThrow();
            Category negocios = categoryRepository.findByName("Negócios").orElseThrow();
            Category ciencias = categoryRepository.findByName("Ciências").orElseThrow();

            List<Book> books = List.of(
                    Book.builder()
                            .title("Java Essencial")
                            .isbnNumber("9780000000001")
                            .description("Livro introdutório sobre a linguagem Java.")
                            .price(24.99)
                            .quantity(10)
                            .author(luis)
                            .category(tecnologia)
                            .build(),

                    Book.builder()
                            .title("Redes de Computadores")
                            .isbnNumber("9780000000002")
                            .description("Fundamentos de redes e comunicações em sistemas modernos.")
                            .price(29.99)
                            .quantity(7)
                            .author(mariana)
                            .category(tecnologia)
                            .build(),

                    Book.builder()
                            .title("Romance do Século")
                            .isbnNumber("9780000000003")
                            .description("Um romance dramático que marcou uma geração.")
                            .price(19.99)
                            .quantity(15)
                            .author(mariana)
                            .category(literatura)
                            .build(),

                    Book.builder()
                            .title("Encantos de Éteria")
                            .isbnNumber("9780000000004")
                            .description("Uma viagem mágica por um mundo de fantasia e mistério.")
                            .price(22.50)
                            .quantity(12)
                            .author(joseN)
                            .category(literatura)
                            .build(),

                    Book.builder()
                            .title("Marketing Digital para Iniciantes")
                            .isbnNumber("9780000000005")
                            .description("Guia prático para estratégias de marketing online.")
                            .price(27.90)
                            .quantity(8)
                            .author(joseQ)
                            .category(negocios)
                            .build(),

                    Book.builder()
                            .title("Bases de Dados Relacionais")
                            .isbnNumber("9780000000006")
                            .description("Aprenda a modelar, criar e consultar bases de dados.")
                            .price(31.40)
                            .quantity(9)
                            .author(luis)
                            .category(tecnologia)
                            .build(),

                    Book.builder()
                            .title("O Universo e o Tempo")
                            .isbnNumber("9780000000007")
                            .description("Uma introdução acessível à física moderna e ao cosmos.")
                            .price(28.99)
                            .quantity(6)
                            .author(joseN)
                            .category(ciencias)
                            .build(),

                    Book.builder()
                            .title("Finanças Pessoais Descomplicadas")
                            .isbnNumber("9780000000008")
                            .description("Tudo o que precisa para organizar as suas finanças com sucesso.")
                            .price(18.75)
                            .quantity(13)
                            .author(mariana)
                            .category(negocios)
                            .build()
            );

            bookRepository.saveAll(books);

            System.out.println("Default books were created!");
        } else {
            System.out.println("Default books already exist!");
        }
    }
}
