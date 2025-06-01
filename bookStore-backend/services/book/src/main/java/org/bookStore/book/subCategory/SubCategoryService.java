package org.bookStore.book.subCategory;

import lombok.RequiredArgsConstructor;
import org.bookStore.book.exception.custom.SubCategoryAlreadyExistsException;
import org.bookStore.book.response.PageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    public PageResponse<SubCategoryResponse> getAllSubCategories(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
        Page<SubCategory> subCategories = subCategoryRepository.findAll(pageable);

        List<SubCategoryResponse> response = subCategories.stream()
                .map(subCategoryMapper::toSubCategoryResponse)
                .toList();

        return new PageResponse<>(
                response,
                subCategories.getNumber(),
                subCategories.getSize(),
                subCategories.getTotalElements(),
                subCategories.getTotalPages(),
                subCategories.isFirst(),
                subCategories.isLast()
        );
    }

}