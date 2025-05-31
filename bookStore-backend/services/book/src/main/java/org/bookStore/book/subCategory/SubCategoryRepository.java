package org.bookStore.book.subCategory;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SubCategoryRepository extends JpaRepository<SubCategory, Long> {
    boolean existsByNameIgnoreCase(String name);
}
