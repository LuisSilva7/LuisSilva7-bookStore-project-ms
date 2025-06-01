package org.bookStore.book.subCategory;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.bookStore.book.response.ApiResponse;
import org.bookStore.book.response.PageResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/sub-categories")
@RequiredArgsConstructor
public class SubCategoryController {

    private final SubCategoryService subCategoryService;

    @PostMapping
    public ResponseEntity<ApiResponse<SubCategoryResponse>> createSubCategory(@RequestBody @Valid CreateSubCategoryRequest request) {
        SubCategoryResponse createdSubCategory = subCategoryService.createSubCategory(request);

        return ResponseEntity.ok(new ApiResponse<>(
                "SubCategory created successfully!", createdSubCategory));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<SubCategoryResponse>>> getAllSubCategories(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size
    ) {
        PageResponse<SubCategoryResponse> subCategories = subCategoryService.getAllSubCategories(page, size);

        return ResponseEntity.ok(new ApiResponse<>(
                "SubCategories obtained successfully!", subCategories));
    }
}
