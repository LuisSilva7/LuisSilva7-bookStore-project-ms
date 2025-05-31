package org.bookStore.book.author;

import lombok.RequiredArgsConstructor;
import org.bookStore.book.exception.custom.AuthorAlreadyExistsException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    public AuthorResponse createAuthor(CreateAuthorRequest request) {
        boolean exists = authorRepository.existsByNameIgnoreCase(request.name());
        if (exists) {
            throw new AuthorAlreadyExistsException(
                    "An author with the name '" + request.name() + "' already exists.");
        }

        Author saved = authorRepository.save(authorMapper.toAuthor(request));
        return authorMapper.toAuthorResponse(saved);
    }

    public List<AuthorResponse> getAllAuthors() {
        List<Author> authors = authorRepository.findAll();

        return authors.stream()
                .map(authorMapper::toAuthorResponse)
                .toList();
    }
}
