package org.bookStore.book.book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("SELECT b FROM Book b " +
            "WHERE (:query IS NULL OR " +
            "LOWER(b.title) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(b.author.name) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(b.category.name) LIKE LOWER(CONCAT('%', :query, '%')))")
    List<Book> searchBooks(@Param("query") String query);

    List<Book> findByCategory_Id(Long categoryId);

    List<Book> findAllByAuthor_Id(Long authorId);
}
