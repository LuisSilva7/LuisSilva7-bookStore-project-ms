package org.bookStore.book.category;

import lombok.RequiredArgsConstructor;
import org.bookStore.book.subCategory.SubCategoryMapper;
import org.bookStore.book.subCategory.SubCategoryResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryMapper {

    private final SubCategoryMapper subCategoryMapper;

    public CategoryResponse toCategoryResponse(Category category) {
        if(category == null) return null;

        List<SubCategoryResponse> subCategoryResponses = null;
        if (category.getSubCategories() != null) {
            subCategoryResponses = category.getSubCategories()
                    .stream()
                    .map(subCategoryMapper::toSubCategoryResponse)
                    .toList();
        }

        return new CategoryResponse(
                category.getId(),
                category.getName(),
                subCategoryResponses
        );
    }

    public Category toCategory(CreateCategoryRequest request) {
        if (request == null) return null;

        Category category = new Category();
        category.setName(request.name());
        return category;
    }
}
