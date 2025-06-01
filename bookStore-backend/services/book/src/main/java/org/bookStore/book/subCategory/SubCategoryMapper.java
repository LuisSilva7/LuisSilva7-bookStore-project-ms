package org.bookStore.book.subCategory;

import lombok.RequiredArgsConstructor;
import org.bookStore.book.category.CategoryMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubCategoryMapper {

    private final CategoryMapper categoryMapper;

    public SubCategoryResponse toSubCategoryResponse(SubCategory subCategory) {
        if(subCategory == null) return null;

        return new SubCategoryResponse(
                subCategory.getId(),
                subCategory.getName(),
                categoryMapper.toCategoryResponse(subCategory.getCategory())
        );
    }

    public SubCategory toSubCategory(CreateSubCategoryRequest request) {
        if (request == null) return null;

        SubCategory subCategory = new SubCategory();
        subCategory.setName(request.name());
        return subCategory;
    }
}
