package org.bookStore.book.category;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.bookStore.book.response.ApiResponse;
import org.bookStore.book.response.PageResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<ApiResponse<CategoryResponse>> createCategory(@RequestBody @Valid CreateCategoryRequest request) {
        CategoryResponse createdCategory = categoryService.createCategory(request);

        return ResponseEntity.ok(new ApiResponse<>(
                "Category created successfully!", createdCategory));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<CategoryResponse>>> getAllCategories(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size
    ) {
        PageResponse<CategoryResponse> categories = categoryService.getAllCategories(page, size);

        return ResponseEntity.ok(new ApiResponse<>(
                "Categories obtained successfully!", categories));
    }
}