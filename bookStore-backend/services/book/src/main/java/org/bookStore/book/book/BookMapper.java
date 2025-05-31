package org.bookStore.book.book;

import lombok.RequiredArgsConstructor;
import org.bookStore.book.author.Author;
import org.bookStore.book.author.AuthorMapper;
import org.bookStore.book.author.AuthorRepository;
import org.bookStore.book.category.Category;
import org.bookStore.book.category.CategoryMapper;
import org.bookStore.book.category.CategoryRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookMapper {

    private final CategoryMapper categoryMapper;
    private final AuthorMapper authorMapper;
    private final CategoryRepository categoryRepository;
    private final AuthorRepository authorRepository;


    public BookResponse toBookResponse(Book book) {
        if(book == null) return null;

        return new BookResponse(
                book.getId(),
                book.getTitle(),
                book.getIsbnNumber(),
                book.getDescription(),
                book.getPrice(),
                book.getQuantity(),
                categoryMapper.toCategoryResponse(book.getCategory()),
                authorMapper.toAuthorResponse(book.getAuthor())
        );
    }

    public Book toBook(CreateBookRequest request) {
        if (request == null) return null;

        Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new IllegalArgumentException("Category not found with id " + request.categoryId()));

        Author author = authorRepository.findById(request.authorId())
                .orElseThrow(() -> new IllegalArgumentException("Author not found with id " + request.authorId()));

        Book book = Book.builder()
                .title(request.title())
                .isbnNumber(request.isbnNumber())
                .description(request.description())
                .price(request.price())
                .quantity(request.quantity())
                .category(category)
                .author(author)
                .build();

        return book;
    }
}
