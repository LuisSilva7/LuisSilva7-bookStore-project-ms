package org.bookStore.book.defaultData;

import lombok.RequiredArgsConstructor;
import org.bookStore.book.category.Category;
import org.bookStore.book.subCategory.SubCategory;
import org.bookStore.book.category.CategoryRepository;
import org.bookStore.book.subCategory.SubCategoryRepository;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.stream.Stream;

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
            SubCategory sub4 = SubCategory.builder().name("Segurança Informática").build();
            SubCategory sub5 = SubCategory.builder().name("Inteligência Artificial").build();

            SubCategory sub6 = SubCategory.builder().name("Romance").build();
            SubCategory sub7 = SubCategory.builder().name("Fantasia").build();
            SubCategory sub8 = SubCategory.builder().name("Histórico").build();
            SubCategory sub9 = SubCategory.builder().name("Terror").build();
            SubCategory sub10 = SubCategory.builder().name("Poesia").build();

            SubCategory sub11 = SubCategory.builder().name("Física").build();
            SubCategory sub12 = SubCategory.builder().name("Biologia").build();
            SubCategory sub13 = SubCategory.builder().name("Astronomia").build();
            SubCategory sub14 = SubCategory.builder().name("Matemática").build();

            SubCategory sub15 = SubCategory.builder().name("Gestão").build();
            SubCategory sub16 = SubCategory.builder().name("Economia").build();
            SubCategory sub17 = SubCategory.builder().name("Marketing").build();
            SubCategory sub18 = SubCategory.builder().name("Finanças Pessoais").build();

            SubCategory sub19 = SubCategory.builder().name("Produtividade").build();
            SubCategory sub20 = SubCategory.builder().name("Motivação").build();
            SubCategory sub21 = SubCategory.builder().name("Mindfulness").build();
            SubCategory sub22 = SubCategory.builder().name("Relacionamentos").build();

            subCategoryRepository.saveAll(List.of(
                    sub1, sub2, sub3, sub4, sub5,
                    sub6, sub7, sub8, sub9, sub10,
                    sub11, sub12, sub13, sub14,
                    sub15, sub16, sub17, sub18,
                    sub19, sub20, sub21, sub22
            ));

            Category tecnologia = Category.builder()
                    .name("Tecnologia")
                    .subCategories(List.of(sub1, sub2, sub3, sub4, sub5))
                    .build();

            Category literatura = Category.builder()
                    .name("Literatura")
                    .subCategories(List.of(sub6, sub7, sub8, sub9, sub10))
                    .build();

            Category ciencias = Category.builder()
                    .name("Ciências")
                    .subCategories(List.of(sub11, sub12, sub13, sub14))
                    .build();

            Category negocios = Category.builder()
                    .name("Negócios")
                    .subCategories(List.of(sub15, sub16, sub17, sub18))
                    .build();

            Category autoajuda = Category.builder()
                    .name("Autoajuda")
                    .subCategories(List.of(sub19, sub20, sub21, sub22))
                    .build();

            Stream.of(tecnologia, literatura, ciencias, negocios, autoajuda)
                    .forEach(cat -> cat.getSubCategories().forEach(sub -> sub.setCategory(cat)));

            categoryRepository.saveAll(List.of(tecnologia, literatura, ciencias, negocios, autoajuda));

            System.out.println("Default categories and subcategories were created!");
        } else {
            System.out.println("Default categories and subcategories already exist!");
        }
    }
}
