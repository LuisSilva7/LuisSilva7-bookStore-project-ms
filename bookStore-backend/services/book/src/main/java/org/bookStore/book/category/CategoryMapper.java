package org.bookStore.book.category;

import org.springframework.stereotype.Service;

@Service
public class CategoryMapper {

    public CategoryResponse toCategoryResponse(Category category) {
        if (category == null) return null;

        return new CategoryResponse(
                category.getId(),
                category.getName(),
                null
        );
    }

    public Category toCategory(CreateCategoryRequest request) {
        if (request == null) return null;

        Category category = new Category();
        category.setName(request.name());
        return category;
    }
}
