package org.bookStore.book.subCategory;

import org.springframework.stereotype.Service;

@Service
public class SubCategoryMapper {

    public SubCategoryResponse toSubCategoryResponse(SubCategory subCategory) {
        if (subCategory == null) return null;

        return new SubCategoryResponse(
                subCategory.getId(),
                subCategory.getName(),
                null
        );
    }

    public SubCategory toSubCategory(CreateSubCategoryRequest request) {
        if (request == null) return null;

        SubCategory subCategory = new SubCategory();
        subCategory.setName(request.name());
        return subCategory;
    }
}
