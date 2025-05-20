package org.bookStore.book.defaultData;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.bookStore.book.author.Author;
import org.bookStore.book.author.AuthorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class DefaultAuthorData implements CommandLineRunner {

    private final AuthorRepository authorRepository;

    @Override
    @Transactional
    public void run(String... args) {

        if (authorRepository.count() == 0) {

            List<Author> authors = List.of(
                    Author.builder().name("Luís Silva").build(),
                    Author.builder().name("Mariana Carneiro").build(),
                    Author.builder().name("José Novais").build(),
                    Author.builder().name("José Queirós").build()
            );

            authorRepository.saveAll(authors);

            System.out.println("Default authors were created!");
        } else {
            System.out.println("Default authors already exist!");
        }
    }
}
