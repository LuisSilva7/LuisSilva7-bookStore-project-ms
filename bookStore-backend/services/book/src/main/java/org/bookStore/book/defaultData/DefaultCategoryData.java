package org.bookStore.book.defaultData;

import lombok.RequiredArgsConstructor;
import org.bookStore.book.category.Category;
import org.bookStore.book.subCategory.SubCategory;
import org.bookStore.book.category.CategoryRepository;
import org.bookStore.book.subCategory.SubCategoryRepository;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class DefaultCategoryData {

    private final CategoryRepository categoryRepository;
    private final SubCategoryRepository subCategoryRepository;

    public void categoryData() {

        if (categoryRepository.count() == 0 && subCategoryRepository.count() == 0) {

            SubCategory sub1 = SubCategory.builder().name("Programação").build();
            SubCategory sub2 = SubCategory.builder().name("Redes").build();
            SubCategory sub3 = SubCategory.builder().name("Base de Dados").build();

            SubCategory sub4 = SubCategory.builder().name("Romance").build();
            SubCategory sub5 = SubCategory.builder().name("Fantasia").build();
            SubCategory sub6 = SubCategory.builder().name("Histórico").build();

            subCategoryRepository.saveAll(Arrays.asList(sub1, sub2, sub3, sub4, sub5, sub6));

            Category tecnologia = Category.builder()
                    .name("Tecnologia")
                    .subCategories(List.of(sub1, sub2, sub3))
                    .build();

            Category literatura = Category.builder()
                    .name("Literatura")
                    .subCategories(List.of(sub4, sub5, sub6))
                    .build();

            tecnologia.getSubCategories().forEach(s -> s.setCategory(tecnologia));
            literatura.getSubCategories().forEach(s -> s.setCategory(literatura));

            categoryRepository.saveAll(List.of(tecnologia, literatura));

            System.out.println("Default categories and subcategories were created!");
        } else {
            System.out.println("Default categories and subcategories already exist!");
        }
    }
}
