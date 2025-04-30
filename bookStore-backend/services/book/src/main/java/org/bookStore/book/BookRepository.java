package org.bookStore.book;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {

    /*@Query("SELECT b FROM Book b " +
            "WHERE (:query is null OR " +
            "b.title LIKE %:query% OR " +
            "b.author.authorName LIKE %:query% OR " +
            "b.category.name LIKE %:query% OR " +
            "b.subcategory.name LIKE %:query%)")
    List<Book> searchBooks(@Param("query") String query);


    List<Book> findByCategoryId(Long id);*/
}
