package org.bookStore.book.subCategory;

import lombok.RequiredArgsConstructor;
import org.bookStore.book.exception.custom.SubCategoryAlreadyExistsException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubCategoryService {

    private final SubCategoryRepository subCategoryRepository;
    private final SubCategoryMapper subCategoryMapper;

    public SubCategoryResponse createSubCategory(CreateSubCategoryRequest request) {
        boolean exists = subCategoryRepository.existsByNameIgnoreCase(request.name());
        if (exists) {
            throw new SubCategoryAlreadyExistsException(
                    "A subCategory with the name '" + request.name() + "' already exists.");
        }

        SubCategory saved = subCategoryRepository.save(subCategoryMapper.toSubCategory(request));
        return subCategoryMapper.toSubCategoryResponse(saved);
    }

    public List<SubCategoryResponse> getAllSubCategories() {
        List<SubCategory> subCategories = subCategoryRepository.findAll();

        return subCategories.stream()
                .map(subCategoryMapper::toSubCategoryResponse)
                .toList();
    }
}