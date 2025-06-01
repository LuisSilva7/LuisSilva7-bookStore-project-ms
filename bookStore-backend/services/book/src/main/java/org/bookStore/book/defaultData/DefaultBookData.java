package org.bookStore.book.defaultData;

import lombok.RequiredArgsConstructor;
import org.bookStore.book.author.Author;
import org.bookStore.book.author.AuthorRepository;
import org.bookStore.book.book.Book;
import org.bookStore.book.book.BookRepository;
import org.bookStore.book.category.Category;
import org.bookStore.book.category.CategoryRepository;
import org.bookStore.book.subCategory.SubCategory;
import org.bookStore.book.subCategory.SubCategoryRepository;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Set;

@Configuration
@RequiredArgsConstructor
public class DefaultBookData {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;
    private final SubCategoryRepository subCategoryRepository;

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

            SubCategory java = subCategoryRepository.findByName("Java").orElseThrow();
            SubCategory redes = subCategoryRepository.findByName("Redes de Computadores").orElseThrow();
            SubCategory romance = subCategoryRepository.findByName("Romance").orElseThrow();
            SubCategory fantasia = subCategoryRepository.findByName("Fantasia").orElseThrow();
            SubCategory marketing = subCategoryRepository.findByName("Marketing Digital").orElseThrow();
            SubCategory basesDados = subCategoryRepository.findByName("Bases de Dados").orElseThrow();
            SubCategory fisica = subCategoryRepository.findByName("Física").orElseThrow();
            SubCategory financas = subCategoryRepository.findByName("Finanças Pessoais").orElseThrow();

            List<Book> books = List.of(
                    Book.builder()
                            .title("Java Essencial")
                            .isbnNumber("9780000000001")
                            .description("Livro introdutório sobre a linguagem Java.")
                            .price(24.99)
                            .quantity(100)
                            .author(luis)
                            .category(tecnologia)
                            .subCategories(Set.of(java))
                            .build(),

                    Book.builder()
                            .title("Redes de Computadores")
                            .isbnNumber("9780000000002")
                            .description("Fundamentos de redes e comunicações em sistemas modernos.")
                            .price(29.99)
                            .quantity(70)
                            .author(mariana)
                            .category(tecnologia)
                            .subCategories(Set.of(redes))
                            .build(),

                    Book.builder()
                            .title("Romance do Século")
                            .isbnNumber("9780000000003")
                            .description("Um romance dramático que marcou uma geração.")
                            .price(19.99)
                            .quantity(150)
                            .author(mariana)
                            .category(literatura)
                            .subCategories(Set.of(romance))
                            .build(),

                    Book.builder()
                            .title("Encantos de Éteria")
                            .isbnNumber("9780000000004")
                            .description("Uma viagem mágica por um mundo de fantasia e mistério.")
                            .price(22.50)
                            .quantity(120)
                            .author(joseN)
                            .category(literatura)
                            .subCategories(Set.of(fantasia))
                            .build(),

                    Book.builder()
                            .title("Marketing Digital para Iniciantes")
                            .isbnNumber("9780000000005")
                            .description("Guia prático para estratégias de marketing online.")
                            .price(27.90)
                            .quantity(80)
                            .author(joseQ)
                            .category(negocios)
                            .subCategories(Set.of(marketing))
                            .build(),

                    Book.builder()
                            .title("Bases de Dados Relacionais")
                            .isbnNumber("9780000000006")
                            .description("Aprenda a modelar, criar e consultar bases de dados.")
                            .price(31.40)
                            .quantity(90)
                            .author(luis)
                            .category(tecnologia)
                            .subCategories(Set.of(basesDados))
                            .build(),

                    Book.builder()
                            .title("O Universo e o Tempo")
                            .isbnNumber("9780000000007")
                            .description("Uma introdução acessível à física moderna e ao cosmos.")
                            .price(28.99)
                            .quantity(60)
                            .author(joseN)
                            .category(ciencias)
                            .subCategories(Set.of(fisica))
                            .build(),

                    Book.builder()
                            .title("Finanças Pessoais Descomplicadas")
                            .isbnNumber("9780000000008")
                            .description("Tudo o que precisa para organizar as suas finanças com sucesso.")
                            .price(18.75)
                            .quantity(130)
                            .author(mariana)
                            .category(negocios)
                            .subCategories(Set.of(financas))
                            .build()
            );

            bookRepository.saveAll(books);

            System.out.println("Default books were created!");
        } else {
            System.out.println("Default books already exist!");
        }
    }
}
