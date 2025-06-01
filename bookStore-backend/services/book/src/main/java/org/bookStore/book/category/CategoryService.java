package org.bookStore.book.category;

import lombok.RequiredArgsConstructor;
import org.bookStore.book.exception.custom.CategoryAlreadyExistsException;
import org.bookStore.book.response.PageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    public PageResponse<CategoryResponse> getAllCategories(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
        Page<Category> categories = categoryRepository.findAll(pageable);

        List<CategoryResponse> response = categories.stream()
                .map(categoryMapper::toCategoryResponse)
                .toList();

        return new PageResponse<>(
                response,
                categories.getNumber(),
                categories.getSize(),
                categories.getTotalElements(),
                categories.getTotalPages(),
                categories.isFirst(),
                categories.isLast()
        );
    }

}
