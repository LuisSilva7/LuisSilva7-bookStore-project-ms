package org.bookStore.book.category;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.bookStore.book.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<ApiResponse<List<CategoryResponse>>> getAllCategories() {
        List<CategoryResponse> categories = categoryService.getAllCategories();

        return ResponseEntity.ok(new ApiResponse<>(
                "Categories obtained successfully!", categories));
    }
}