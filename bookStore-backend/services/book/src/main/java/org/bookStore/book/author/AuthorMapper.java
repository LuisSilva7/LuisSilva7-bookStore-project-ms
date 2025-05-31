package org.bookStore.book.author;

import org.springframework.stereotype.Service;

@Service
public class AuthorMapper {

    public AuthorResponse toAuthorResponse(Author author) {
        if(author == null) return null;

        return new AuthorResponse(
                author.getId(),
                author.getName()
        );
    }

    public Author toAuthor(CreateAuthorRequest request) {
        if (request == null) return null;

        Author author = new Author();
        author.setName(request.name());
        return author;
    }
}
