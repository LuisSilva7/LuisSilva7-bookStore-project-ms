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

            Author luis = authorRepository.findByName("Luís Silva")
                    .orElseThrow(() -> new RuntimeException("Author 'Luís Silva' not found"));
            Author mariana = authorRepository.findByName("Mariana Carneiro")
                    .orElseThrow(() -> new RuntimeException("Author 'Mariana Carneiro' not found"));
            Author joseN = authorRepository.findByName("José Novais")
                    .orElseThrow(() -> new RuntimeException("Author 'José Novais' not found"));
            Author joseQ = authorRepository.findByName("José Queirós")
                    .orElseThrow(() -> new RuntimeException("Author 'José Queirós' not found"));

            Category tecnologia = categoryRepository.findByName("Tecnologia")
                    .orElseThrow(() -> new RuntimeException("Category 'Tecnologia' not found"));
            Category literatura = categoryRepository.findByName("Literatura")
                    .orElseThrow(() -> new RuntimeException("Category 'Literatura' not found"));
            Category negocios = categoryRepository.findByName("Negócios")
                    .orElseThrow(() -> new RuntimeException("Category 'Negócios' not found"));
            Category ciencias = categoryRepository.findByName("Ciências")
                    .orElseThrow(() -> new RuntimeException("Category 'Ciências' not found"));

            SubCategory programacao = subCategoryRepository.findByName("Programação")
                    .orElseThrow(() -> new RuntimeException("SubCategory 'Programação' not found"));
            SubCategory redes = subCategoryRepository.findByName("Redes")
                    .orElseThrow(() -> new RuntimeException("SubCategory 'Redes' not found"));
            SubCategory basesDados = subCategoryRepository.findByName("Base de Dados")
                    .orElseThrow(() -> new RuntimeException("SubCategory 'Base de Dados' not found"));
            SubCategory seguranca = subCategoryRepository.findByName("Segurança Informática")
                    .orElseThrow(() -> new RuntimeException("SubCategory 'Segurança Informática' not found"));
            SubCategory inteligencia = subCategoryRepository.findByName("Inteligência Artificial")
                    .orElseThrow(() -> new RuntimeException("SubCategory 'Inteligência Artificial' not found"));

            SubCategory romance = subCategoryRepository.findByName("Romance")
                    .orElseThrow(() -> new RuntimeException("SubCategory 'Romance' not found"));
            SubCategory fantasia = subCategoryRepository.findByName("Fantasia")
                    .orElseThrow(() -> new RuntimeException("SubCategory 'Fantasia' not found"));
            SubCategory historico = subCategoryRepository.findByName("Histórico")
                    .orElseThrow(() -> new RuntimeException("SubCategory 'Histórico' not found"));
            SubCategory terror = subCategoryRepository.findByName("Terror")
                    .orElseThrow(() -> new RuntimeException("SubCategory 'Terror' not found"));
            SubCategory poesia = subCategoryRepository.findByName("Poesia")
                    .orElseThrow(() -> new RuntimeException("SubCategory 'Poesia' not found"));

            SubCategory fisica = subCategoryRepository.findByName("Física")
                    .orElseThrow(() -> new RuntimeException("SubCategory 'Física' not found"));
            SubCategory biologia = subCategoryRepository.findByName("Biologia")
                    .orElseThrow(() -> new RuntimeException("SubCategory 'Biologia' not found"));
            SubCategory astronomia = subCategoryRepository.findByName("Astronomia")
                    .orElseThrow(() -> new RuntimeException("SubCategory 'Astronomia' not found"));
            SubCategory matematica = subCategoryRepository.findByName("Matemática")
                    .orElseThrow(() -> new RuntimeException("SubCategory 'Matemática' not found"));

            SubCategory gestao = subCategoryRepository.findByName("Gestão")
                    .orElseThrow(() -> new RuntimeException("SubCategory 'Gestão' not found"));
            SubCategory economia = subCategoryRepository.findByName("Economia")
                    .orElseThrow(() -> new RuntimeException("SubCategory 'Economia' not found"));
            SubCategory marketing = subCategoryRepository.findByName("Marketing")
                    .orElseThrow(() -> new RuntimeException("SubCategory 'Marketing' not found"));
            SubCategory financas = subCategoryRepository.findByName("Finanças Pessoais")
                    .orElseThrow(() -> new RuntimeException("SubCategory 'Finanças Pessoais' not found"));

            SubCategory produtividade = subCategoryRepository.findByName("Produtividade")
                    .orElseThrow(() -> new RuntimeException("SubCategory 'Produtividade' not found"));
            SubCategory motivacao = subCategoryRepository.findByName("Motivação")
                    .orElseThrow(() -> new RuntimeException("SubCategory 'Motivação' not found"));
            SubCategory mindfulness = subCategoryRepository.findByName("Mindfulness")
                    .orElseThrow(() -> new RuntimeException("SubCategory 'Mindfulness' not found"));
            SubCategory relacionamentos = subCategoryRepository.findByName("Relacionamentos")
                    .orElseThrow(() -> new RuntimeException("SubCategory 'Relacionamentos' not found"));


            List<Book> books = List.of(
                    Book.builder()
                            .title("Java Essencial")
                            .isbnNumber("9780000000001")
                            .description("Livro introdutório sobre a linguagem Java.")
                            .price(24.99)
                            .quantity(100)
                            .author(luis)
                            .category(tecnologia)
                            .subCategories(Set.of(programacao))
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
