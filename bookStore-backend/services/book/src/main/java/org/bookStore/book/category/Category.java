package org.bookStore.book.category;

import jakarta.persistence.*;
import lombok.*;
import org.bookStore.book.book.Book;
import org.bookStore.book.subCategory.SubCategory;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class Category {

    public static final String HttpStatus = null;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Book> books;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<SubCategory> subCategories;
}
