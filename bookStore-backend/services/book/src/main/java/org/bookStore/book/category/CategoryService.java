package org.bookStore.book.category;

import lombok.RequiredArgsConstructor;
import org.bookStore.book.exception.custom.CategoryAlreadyExistsException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryResponse createCategory(CreateCategoryRequest request) {
        boolean exists = categoryRepository.existsByNameIgnoreCase(request.name());
        if (exists) {
            throw new CategoryAlreadyExistsException(
                    "A category with the name '" + request.name() + "' already exists.");
        }

        Category saved = categoryRepository.save(categoryMapper.toCategory(request));
        return categoryMapper.toCategoryResponse(saved);
    }

    public List<CategoryResponse> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();

        return categories.stream()
                .map(categoryMapper::toCategoryResponse)
                .toList();
    }
}
