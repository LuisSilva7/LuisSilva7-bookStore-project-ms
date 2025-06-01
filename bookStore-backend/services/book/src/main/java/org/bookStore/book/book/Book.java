package org.bookStore.book.book;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.bookStore.book.author.Author;
import org.bookStore.book.category.Category;
import org.bookStore.book.subCategory.SubCategory;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
    @JsonIgnore
    @JoinColumn(name = "author_id")
    private Author author;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToMany
    @JoinTable(
            name = "book_subcategory",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "subcategory_id")
    )
    private Set<SubCategory> subCategories = new HashSet<>();
}