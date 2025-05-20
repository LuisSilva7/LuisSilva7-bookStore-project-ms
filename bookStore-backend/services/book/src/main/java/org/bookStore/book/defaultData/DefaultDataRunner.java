package org.bookStore.book.defaultData;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class DefaultDataRunner implements CommandLineRunner {

    private final DefaultAuthorData defaultAuthorData;
    private final DefaultCategoryData defaultCategoryData;
    private final DefaultBookData defaultBookData;

    @Override
    @Transactional
    public void run(String... args) {

        defaultAuthorData.authorData();
        defaultCategoryData.categoryData();
        defaultBookData.bookData();
    }
}
