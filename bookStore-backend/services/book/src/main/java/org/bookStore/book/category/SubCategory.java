package org.bookStore.book.category;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class SubCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sub_category_id")
    private Long subCategoryId;

    @Column(unique = true)
    private String name;

    @ManyToOne
    @JoinColumn(name="category_id")
    private Category category;
}
