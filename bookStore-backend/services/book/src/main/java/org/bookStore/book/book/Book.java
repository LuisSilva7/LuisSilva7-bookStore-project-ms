package org.bookStore.book.book;

import jakarta.persistence.*;
import lombok.*;
import org.bookStore.book.author.Author;
import org.bookStore.book.category.Category;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Long bookId;

    @Column
    private String title;

    @Column(unique = true)
    private String isbnNumber;

    @Column(columnDefinition = "BLOB")
    private byte[] image;

    @Column
    private String description;

    @Column
    private double price;

    @Column
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}