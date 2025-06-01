package org.bookStore.book.subCategory;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubCategoryRepository extends JpaRepository<SubCategory, Long> {
    boolean existsByNameIgnoreCase(String name);

    Optional<SubCategory> findByName(String java);
}
