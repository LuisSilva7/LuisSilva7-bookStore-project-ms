package org.bookStore.book.subCategory;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.bookStore.book.book.Book;
import org.bookStore.book.category.Category;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class SubCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name="category_id")
    private Category category;

    @ManyToMany(mappedBy = "subCategories")
    private Set<Book> books = new HashSet<>();
}
